package com.projet.hpd.service.impl;

import com.projet.hpd.service.ModuleService;
import com.projet.hpd.domain.Module;
import com.projet.hpd.repository.ModuleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Module}.
 */
@Service
@Transactional
public class ModuleServiceImpl implements ModuleService {

    private final Logger log = LoggerFactory.getLogger(ModuleServiceImpl.class);

    private final ModuleRepository moduleRepository;

    public ModuleServiceImpl(ModuleRepository moduleRepository) {
        this.moduleRepository = moduleRepository;
    }

    /**
     * Save a module.
     *
     * @param module the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Module save(Module module) {
        log.debug("Request to save Module : {}", module);
        return moduleRepository.save(module);
    }

    /**
     * Get all the modules.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Module> findAll(Pageable pageable) {
        log.debug("Request to get all Modules");
        return moduleRepository.findAll(pageable);
    }

    /**
     * Get one module by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Module> findOne(Long id) {
        log.debug("Request to get Module : {}", id);
        return moduleRepository.findById(id);
    }

    /**
     * Delete the module by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Module : {}", id);
        moduleRepository.deleteById(id);
    }
}
