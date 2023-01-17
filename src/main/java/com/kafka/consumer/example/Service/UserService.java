package com.kafka.consumer.example.Service;

import com.kafka.consumer.example.Model.UserEntity;
import com.kafka.consumer.example.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

import static java.lang.Math.floor;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public String save(UserEntity userEntity) {
        userRepository.save(userEntity);
        return "done";
    }

    public List<UserEntity> all() {
        List<UserEntity> userEntities = userRepository.sortRank();
        for (int i = 0; i < userEntities.size(); i++) {
            userEntities.get(i).setRank((float) (i+1));
        }
        return userEntities;
    }

    public void delete(UserEntity user) {
        userRepository.deleteById(user.getId());
    }

    public void reorder(UserEntity user) {
        List<UserEntity> users = userRepository.sortRank();
        Map<Integer, UserEntity> rankedUsers = new HashMap<>();
        for (int i = 0; i < users.size(); i++) {
            rankedUsers.put(i+1, users.get(i));
        }
        UserEntity before = rankedUsers.get(user.getRank().intValue() - 1);
        UserEntity after = rankedUsers.get(user.getRank().intValue());
        float rank = 0;
        if (before==null){
            rank = (0 + after.getRank())/2;
        } else if (after == null){
            rank = (before.getRank() + before.getRank()+1)/2;
        }else {
            rank = (before.getRank() + after.getRank()) / 2;
        }
        UserEntity newRankUser = userRepository.getById(user.getId());
        newRankUser.setRank(rank);
        if (significantDigits(rank) >= 5){
            users.add(newRankUser);
            users.sort(Comparator.comparing(UserEntity::getRank));
            userRepository.deleteAll();
            int newRank = 1;
            for (UserEntity userEntity:users) {
                userEntity.setRank((float) newRank);
                newRank++;
            }
            userRepository.saveAll(users);
        }
        else {
            userRepository.save(newRankUser);
        }
    }

    public void reorderFull(UserEntity user) {
        List<UserEntity> users = userRepository.sortRank();
        UserEntity newRankUser = userRepository.getById(user.getId());
            for (UserEntity userEntity:users) {
                if (userEntity.getRank().equals(user.getRank())){
                    newRankUser.setRank(user.getRank());
                }
                if (userEntity.getRank() >= user.getRank()) {
                    userEntity.setRank(userEntity.getRank()+1);
                }
            }
            users.add(newRankUser);
            userRepository.saveAll(users);
    }

    int significantDigits(double value)
    {
        int count = 0;
        for (; value!=floor(value); value *= 10)
            count++;
        return count;
    }

    public void setup() {
        List<UserEntity> userEntities = new ArrayList<>();
        userEntities.add(new UserEntity("1", "Gopal", 20, 1.0f));
        userEntities.add(new UserEntity("2", "Siddhant", 20, 2.0f));
        userEntities.add(new UserEntity("3", "Rohan", 20, 3.0f));
        userEntities.add(new UserEntity("4", "Praveen", 20, 4.0f));
        userEntities.add(new UserEntity("5", "Siddharth", 20, 5.0f));
        userEntities.add(new UserEntity("6", "Devang", 20, 6.0f));
        userEntities.add(new UserEntity("7", "Krutika", 20, 7.0f));
        userEntities.add(new UserEntity("8", "Mehul", 20, 8.0f));
        userEntities.add(new UserEntity("9", "Umar", 20, 9.0f));
        userEntities.add(new UserEntity("10", "Bhavesh", 20, 10.0f));
        userRepository.saveAll(userEntities);
    }

    public void deleteAll() {
        userRepository.findAll().forEach(o -> userRepository.delete(o));
    }
}
