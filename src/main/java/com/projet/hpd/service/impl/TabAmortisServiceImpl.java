package com.projet.hpd.service.impl;

import com.projet.hpd.service.TabAmortisService;
import com.projet.hpd.domain.TabAmortis;
import com.projet.hpd.repository.TabAmortisRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TabAmortis}.
 */
@Service
@Transactional
public class TabAmortisServiceImpl implements TabAmortisService {

    private final Logger log = LoggerFactory.getLogger(TabAmortisServiceImpl.class);

    private final TabAmortisRepository tabAmortisRepository;

    public TabAmortisServiceImpl(TabAmortisRepository tabAmortisRepository) {
        this.tabAmortisRepository = tabAmortisRepository;
    }

    /**
     * Save a tabAmortis.
     *
     * @param tabAmortis the entity to save.
     * @return the persisted entity.
     */
    @Override
    public TabAmortis save(TabAmortis tabAmortis) {
        log.debug("Request to save TabAmortis : {}", tabAmortis);
        return tabAmortisRepository.save(tabAmortis);
    }

    /**
     * Get all the tabAmortis.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TabAmortis> findAll(Pageable pageable) {
        log.debug("Request to get all TabAmortis");
        return tabAmortisRepository.findAll(pageable);
    }

    /**
     * Get one tabAmortis by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TabAmortis> findOne(Long id) {
        log.debug("Request to get TabAmortis : {}", id);
        return tabAmortisRepository.findById(id);
    }

    /**
     * Delete the tabAmortis by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TabAmortis : {}", id);
        tabAmortisRepository.deleteById(id);
    }
}
