package com.hasan.movieproject.service.user.impl;

import com.hasan.movieproject.model.user.repository.UserRepository;
import com.hasan.movieproject.service.util.ServiceUtil;
import com.hasan.movieproject.log.base.Operation;
import com.hasan.movieproject.log.base.OperationStatus;
import com.hasan.movieproject.model.movie.dao.MovieEntity;
import com.hasan.movieproject.model.user.dao.UserEntity;
import com.hasan.movieproject.model.user.repository.ListRepository;
import com.hasan.movieproject.model.user.dao.ListEntity;
import com.hasan.movieproject.service.user.base.ListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListServiceImpl implements ListService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ListRepository listRepository;
    @Autowired
    private ServiceUtil serviceUtil;

    @Override
    public Operation<?> getAll() {
        Iterable<ListEntity> lists = listRepository.findAll();

        if(lists != null){
            return new Operation<>(OperationStatus.LIST_ENTITY_FOUND, lists);
        } else{
            return new Operation<>(OperationStatus.LIST_ENTITY_NOT_FOUND);
        }
    }

    @Override
    public Operation<?> add(String username, String type, Long movieId) {
        ListEntity listEntity = null;
        UserEntity user = serviceUtil.getUser(username);
        MovieEntity movie = serviceUtil.getMovie(movieId);

        if(movie == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND); }
        if(user == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND); }

        for(ListEntity entity: user.getListEntities()){
            if(entity.getType().equals(type)){
                listEntity = entity;
            }
        }

        if(listEntity != null){
            if(listEntity.getMovies().contains(movie)){
                //return List_Movie_Exists
                return new Operation<>(OperationStatus.LIST_ENTITY_EXISTS);
            } else{
                listEntity.getMovies().add(movie);
                listRepository.save(listEntity);
            }
        } else{
            listEntity = new ListEntity();
            listEntity.setType(type);
            listEntity.getMovies().add(movie);
            listRepository.save(listEntity);
            user.getListEntities().add(listEntity);
        }

        userRepository.save(user);
        return new Operation<>(OperationStatus.LIST_ENTITY_ADD_SUCCESS, user.getListEntities());
    }

    @Override
    public Operation<?> search(String username, String type) {
        UserEntity user = serviceUtil.getUser(username);

        if(user == null) { return new Operation<>(OperationStatus.USER_NOT_FOUND); }
        if (user.getListEntities() == null) { return new Operation<>(OperationStatus.LIST_ENTITY_NOT_FOUND); }

        for (ListEntity entity : user.getListEntities()) {
            if (type.equals(entity.getType())) { return new Operation<>(OperationStatus.LIST_ENTITY_FOUND, entity.getMovies()); }
        }
        return new Operation<>(OperationStatus.LIST_ENTITY_NOT_FOUND);
    }

    @Override
    public Operation<?> remove(String username, String type, Long movieId) {
        UserEntity user = serviceUtil.getUser(username);
        MovieEntity movie = serviceUtil.getMovie(movieId);

        if(user == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND);}
        if(movie == null){ return new Operation<>(OperationStatus.MOVIE_NOT_FOUND); }

        for(ListEntity entity: user.getListEntities()){
            if(type.equals(entity.getType()) && entity.getMovies().contains(movie) && entity.getMovies().remove(movie)){
                userRepository.save(user);
                return new Operation<>(OperationStatus.LIST_ENTITY_REMOVE_SUCCESS, user.getListEntities());
            }
        }

        return new Operation<>(OperationStatus.LIST_ENTITY_NOT_FOUND);
    }

    @Override
    public Operation getListsOf(String username) {
        UserEntity user = serviceUtil.getUser(username);
        if(user == null){return new Operation<>(OperationStatus.USER_NOT_FOUND);}

        return new Operation<>(OperationStatus.LIST_ENTITY_FOUND, user.getListEntities());
    }

    @Override
    public Operation removeListType(String username, String type) {
        UserEntity user = serviceUtil.getUser(username);

        if(user == null){ return new Operation<>(OperationStatus.USER_NOT_FOUND);}

        for(ListEntity entity: user.getListEntities()){
            if(type.equals(entity.getType())){
                if(user.getListEntities().remove(entity)){
                    userRepository.save(user);
                    return new Operation<>(OperationStatus.LIST_ENTITY_REMOVE_SUCCESS, user.getListEntities());
                } else{
                    return new Operation<>(OperationStatus.LIST_ENTITY_REMOVE_FAILED);
                }
            }
        }

        return new Operation<>(OperationStatus.LIST_ENTITY_NOT_FOUND);
    }
}
