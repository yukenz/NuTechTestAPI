package nutech.awan.ppob.repository;

import nutech.awan.ppob.model.entity.Member;
import nutech.awan.ppob.repository.interfaces.MemberRepository;
import org.junit.jupiter.api.Test;
import org.opentest4j.TestAbortedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberRepositoryTest {

    @Autowired
    MemberRepository memberRepository;

    @Test
    void findById() {

        Member member = memberRepository.findById("yuyun.purniawan@gmail.com").orElseThrow(() -> new TestAbortedException());

        System.out.println(member);


    }


}
