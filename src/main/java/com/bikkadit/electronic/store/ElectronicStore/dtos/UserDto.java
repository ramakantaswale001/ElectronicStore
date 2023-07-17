package com.bikkadit.electronic.store.ElectronicStore.dtos;

import com.bikkadit.electronic.store.ElectronicStore.entities.BaseEntities;
import com.bikkadit.electronic.store.ElectronicStore.validate.ImageNameValid;
import lombok.*;

import javax.validation.constraints.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends BaseEntities {

    private String userId;

    @NotEmpty
    @Size(min = 3, message = "name must be min of 3 charactors!!")
    private String name;

    //@Email(message = "email address is not valid")
    @Pattern(regexp = "^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$", message = "email address is not valid")
    private String email;

    @NotEmpty(message = "please enter password")
    @Size(min = 3, max = 10, message = "password must be btw 3-10 charactors")
   // @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;

    @Size(min = 4, max = 6, message = "Invalid gender !!")
    private String gender;

    @NotBlank(message = "Write something about yourself !!")
    private String about;

    @ImageNameValid
    private String imageName;
}
