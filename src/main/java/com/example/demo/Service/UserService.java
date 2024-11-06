package com.example.demo.Service;

import com.example.demo.Exceptions.UserNotFoundException;
import com.example.demo.entity.LogInLogOutTime;
import com.example.demo.entity.User;
import com.example.demo.repository.LogInLogOutTimeRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogInLogOutTimeRepository logInLogOutTimeRepository;

    private static final long coolDownPeriod =3;

    public void saveUser(User user){
         userRepository.save(user);
    }

    public Optional<User> findUserByUid(String uid){
        return userRepository.findByNfcTagUid(uid);
    }

    public String logUserAttendance(String uid) throws UserNotFoundException {
        User user = findUserByUid(uid)
                .orElseThrow(() -> new UserNotFoundException("User not found with uid " + uid));

        Optional<LogInLogOutTime> lastLogOpt = user.getLogInLogOutTimeList().stream()
                .max(Comparator.comparing(LogInLogOutTime::getLoginTime));

        if (lastLogOpt.isPresent()) {
            LogInLogOutTime lastLog = lastLogOpt.get();
            LocalDateTime lastActionTime = (lastLog.getLogoutTime() != null) ? lastLog.getLogoutTime() : lastLog.getLoginTime();
            if (Duration.between(lastActionTime, LocalDateTime.now()).toMinutes() < coolDownPeriod) {
                return "Please wait 3 minutes before logging again.";
            }
        }

        if (lastLogOpt.isPresent() && lastLogOpt.get().getLogoutTime() == null) {
            LogInLogOutTime lastLog = lastLogOpt.get();
            lastLog.setLogoutTime(LocalDateTime.now());
            logInLogOutTimeRepository.save(lastLog);
            return "Logged out successfully.";
        } else {
            LogInLogOutTime newLog = new LogInLogOutTime();
            newLog.setLoginTime(LocalDateTime.now());
            newLog.setUser(user);
            logInLogOutTimeRepository.save(newLog);
            return "Logged in successfully.";
        }
    }

    public Optional<Duration> getTotalWorkingHours(String uid) throws UserNotFoundException {
        User user = findUserByUid(uid).orElseThrow(() -> new UserNotFoundException("User not found with uid " + uid));

        List<LogInLogOutTime> logList = user.getLogInLogOutTimeList();
        Duration totalDuration = Duration.ZERO;
        for (LogInLogOutTime log : logList) {
            if (log.getLoginTime() != null && log.getLogoutTime() != null) {
                Duration duration = Duration.between(log.getLoginTime(), log.getLogoutTime());
                totalDuration = totalDuration.plus(duration);
            }
        }
        return Optional.ofNullable(totalDuration);
    }

    public Optional<Duration> getTotalWorkingHoursSpecifiedDay(String uid, LocalDate date ) throws UserNotFoundException {
        User user = findUserByUid(uid)
                .orElseThrow(() -> new UserNotFoundException("User not found with uid " + uid));
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<LogInLogOutTime> logListforSpecificDay = logInLogOutTimeRepository.findAllByUserAndLoginTimeBetween(user,
                                                                                                                 startOfDay,
                                                                                                                 endOfDay);
        Duration totalDuration = Duration.ZERO;
        for (LogInLogOutTime log : logListforSpecificDay) {
            if (log.getLoginTime() != null && log.getLogoutTime() != null) {
                Duration duration = Duration.between(log.getLoginTime(), log.getLogoutTime());
                totalDuration = totalDuration.plus(duration);
            }
        }
        return Optional.ofNullable(totalDuration);
    }

    public Boolean CheckIfUserHasWorkedEnough(String uid, LocalDate date) throws UserNotFoundException {
        User user = findUserByUid(uid)
                .orElseThrow(() -> new UserNotFoundException("User not found with uid " + uid));
        long totalHoursWorked = getTotalWorkingHoursSpecifiedDay(uid, date).get().toHours();
        if(totalHoursWorked >= 8)
            return true;
        else return false;
    }
}
