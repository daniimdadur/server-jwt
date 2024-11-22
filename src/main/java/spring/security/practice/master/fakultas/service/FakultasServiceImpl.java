package spring.security.practice.master.fakultas.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import spring.security.practice.master.fakultas.model.FakultasEntity;
import spring.security.practice.master.fakultas.model.FakultasReq;
import spring.security.practice.master.fakultas.model.FakultasRes;
import spring.security.practice.master.fakultas.repo.FakultasRepo;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FakultasServiceImpl implements FakultasService{
    private final FakultasRepo fakultasRepo;

    @Override
    public List<FakultasRes> get() {
        List<FakultasEntity> result = this.fakultasRepo.findAll();
        if (result.isEmpty()) {
            return Collections.emptyList();
        }

        return result.stream().map(this::convertEntityToRes).collect(Collectors.toList());
    }

    @Override
    public Optional<FakultasRes> getById(String id) {
        return Optional.empty();
    }

    @Override
    public Optional<FakultasRes> save(FakultasReq request) {
        return Optional.empty();
    }

    @Override
    public Optional<FakultasRes> update(FakultasReq request, String id) {
        return Optional.empty();
    }

    @Override
    public Optional<FakultasRes> delete(String id) {
        return Optional.empty();
    }

    private FakultasRes convertEntityToRes(FakultasEntity entity) {
        FakultasRes result = new FakultasRes();
        BeanUtils.copyProperties(entity, result);
        return result;
    }
}
