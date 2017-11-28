package rizki.practicum.learning.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rizki.practicum.learning.util.Confirmation;
import rizki.practicum.learning.entity.User;
import rizki.practicum.learning.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Confirmation createUser(User user) {
        if(user.getPassword()!=null
                && user.getEmail()!=null
                && user.getName()!=null
                && user.getIdentity()!=null){
            try{
                userRepository.save(user);
                return new Confirmation(true,"Pengguna berhasil dibuat");
            }catch (Exception e){
                return new Confirmation(false,"Error : "+e);
            }
        }else{
            return new Confirmation(false,"Masukan tidak lengkap");
        }
    }

    public Confirmation removeUser(String id){
        if(id!=null){
            try{
                userRepository.delete(id);
                return new Confirmation(true, "Pengguna berhasil dihapus");
            }catch (Exception e){
                return new Confirmation(false,"Error : "+e);
            }
        }else{
            return new Confirmation(false,"Silahkan Pilih Pengguna");
        }
    }


}
