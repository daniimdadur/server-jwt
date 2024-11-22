package spring.security.practice.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import spring.security.practice.master.fakultas.model.FakultasEntity;
import spring.security.practice.master.fakultas.repo.FakultasRepo;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DbInit implements CommandLineRunner {
    private final FakultasRepo fakultasRepo;

    @Override
    public void run(String... args) throws Exception {
        initFakultas();
    }

    private void initFakultas() {
        if (!this.fakultasRepo.findAll().isEmpty()) {
            return;
        }

        List<FakultasEntity> fakultasList = new ArrayList<>();
        FakultasEntity teknik = new FakultasEntity("1809665118ac415583736e8bf4633cbc", "F001", "FT");
        FakultasEntity kedokteran = new FakultasEntity("2bb072366f7c4c018752aabe5e25515a", "F002", "FK");
        FakultasEntity dakwah = new FakultasEntity("3adfc34dab524c30b9d54b91a110b67e", "F003", "FD");

        //generate data
        fakultasList.add(teknik);
        fakultasList.add(kedokteran);
        fakultasList.add(dakwah);

        try {
            this.fakultasRepo.saveAll(fakultasList);
            log.info("fakultas saved");
        } catch (Exception e) {
            log.error("save fakultas failed, error {}", e.getMessage());
        }
    }
}
