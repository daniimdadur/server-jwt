package spring.security.practice;

import org.junit.jupiter.api.Test;
import spring.security.practice.util.CommonUtil;

public class GenerateCustomId {
    @Test
    public void Id() {
        for (int i = 0; i < 10; i++) {
            System.out.println(CommonUtil.getUUID());
        }
    }
}
