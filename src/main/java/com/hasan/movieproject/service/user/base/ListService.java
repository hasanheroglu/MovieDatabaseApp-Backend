package com.hasan.movieproject.service.user.base;

import com.hasan.movieproject.log.base.Operation;

public interface ListService {
    Operation getAll();
    Operation add(String username, String type, Long movieId);
    Operation search(String username, String type);
    Operation remove(String username, String type, Long movieId);
    Operation getListsOf(String username);
    Operation removeListType(String username, String type);
}
