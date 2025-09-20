package com.ektaara.open_gem_gem.service;


import com.ektaara.open_gem_gem.entity.Store;

import java.util.List;

public interface StoreService {
    Store addStore(Store store);
    List<Store> getAllStores();
}
