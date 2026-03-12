package com.ljuslin.inluppgift1_backend.config;

import com.ljuslin.inluppgift1_backend.entity.Address;
import com.ljuslin.inluppgift1_backend.entity.Member;
import com.ljuslin.inluppgift1_backend.repository.AddressRepository;
import com.ljuslin.inluppgift1_backend.repository.AppUserRepository;
import com.ljuslin.inluppgift1_backend.repository.MemberRepository;
import com.ljuslin.inluppgift1_backend.security.AppUser;
import com.ljuslin.inluppgift1_backend.security.Role;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    CommandLineRunner initData(AppUserRepository appUserRepo, PasswordEncoder passwordEncoder,
                               MemberRepository memberRepository, AddressRepository addressRepository) {
        return args -> {
            if (appUserRepo.count() == 0) {
                AppUser admin1 = new AppUser(
                        "admin1",
                        passwordEncoder.encode("admin1"),
                        Role.ADMIN
                );
                AppUser user1 = new AppUser(
                        "user1",
                        passwordEncoder.encode("user1"),
                        Role.USER
                );
                AppUser user2 = new AppUser(
                        "user2",
                        passwordEncoder.encode("user2"),
                        Role.USER
                );
                AppUser user3 = new AppUser(
                        "user3",
                        passwordEncoder.encode("user3"),
                        Role.USER
                );
                AppUser user4 = new AppUser(
                        "user4",
                        passwordEncoder.encode("user4"),
                        Role.USER
                );
                System.out.println("Skapat admin1, user1, user2, password samma som user");
                /*String firstName, String lastName, String email, String phone, String dateOfBirth, Address
                adress*/
                Address address1 = new Address("Alfavägen 12", "835 95", "Askersund");
                Address address2 = new Address("Betagatan 34", "789 56", "Betinge");
                Address address3 = new Address("Ceasarstreet 78", "123 45", "Carlshamn");
                addressRepository.saveAll(List.of(address1, address2, address3));
                Member member1 = new Member("Anna", "Andersson", "anna@mail.se", "070-1111111",
                        "19810101-0101", address1);
                Member member2 = new Member("Bettan", "Bengtsson", "bettan@mail.se", "070-2222222",
                        "19820202-0202", address1);
                Member member3 = new Member("Ceasar", "Carlsson", "ceasar@mail.se", "070-3333333",
                        "19830303-0303", address2);
                Member member4 = new Member("Daniel", "Danielsson", "daniel@mail.se", "070-4444444",
                        "19840404-0404", address3);
                Member member5 = new Member("Erik", "Eriksson", "erik@mail.se", "070-5555555",
                        "19850505-0505", address3);
                admin1.setMember(member1);
                user1.setMember(member2);
                user2.setMember(member3);
                user3.setMember(member4);
                user4.setMember(member5);
                memberRepository.saveAll(List.of(member1, member2, member3, member4, member5));
                appUserRepo.saveAll(List.of(admin1, user1, user2, user3, user4));

            }
        };
    }
}
