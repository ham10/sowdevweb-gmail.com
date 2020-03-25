package com.projet.hpd.service.impl;

import com.projet.hpd.service.GroupeSanService;
import com.projet.hpd.domain.GroupeSan;
import com.projet.hpd.repository.GroupeSanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link GroupeSan}.
 */
@Service
@Transactional
public class GroupeSanServiceImpl implements GroupeSanService {

    private final Logger log = LoggerFactory.getLogger(GroupeSanServiceImpl.class);

    private final GroupeSanRepository groupeSanRepository;

    public GroupeSanServiceImpl(GroupeSanRepository groupeSanRepository) {
        this.groupeSanRepository = groupeSanRepository;
    }

    /**
     * Save a groupeSan.
     *
     * @param groupeSan the entity to save.
     * @return the persisted entity.
     */
    @Override
    public GroupeSan save(GroupeSan groupeSan) {
        log.debug("Request to save GroupeSan : {}", groupeSan);
        return groupeSanRepository.save(groupeSan);
    }

    /**
     * Get all the groupeSans.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<GroupeSan> findAll(Pageable pageable) {
        log.debug("Request to get all GroupeSans");
        return groupeSanRepository.findAll(pageable);
    }

    /**
     * Get one groupeSan by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<GroupeSan> findOne(Long id) {
        log.debug("Request to get GroupeSan : {}", id);
        return groupeSanRepository.findById(id);
    }

    /**
     * Delete the groupeSan by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete GroupeSan : {}", id);
        groupeSanRepository.deleteById(id);
    }
}
