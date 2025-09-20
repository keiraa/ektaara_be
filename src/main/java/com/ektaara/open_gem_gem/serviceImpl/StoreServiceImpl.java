package com.ektaara.open_gem_gem.serviceImpl;


import com.ektaara.open_gem_gem.entity.Store;
import com.ektaara.open_gem_gem.repository.StoreRepository;
import com.ektaara.open_gem_gem.service.StoreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;


    @Override
    public Store addStore(Store store) {
        return storeRepository.save(store);
    }

    @Override
    public List<Store> getAllStores() {
        return storeRepository.findAll();
    }
}
