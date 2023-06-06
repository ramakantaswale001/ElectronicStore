package com.bikkadit.electronic.store.ElectronicStore.dtos;

import com.bikkadit.electronic.store.ElectronicStore.entities.CustomeFields;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto extends CustomeFields {

    private String userId;

    @NotEmpty
    @Size(min = 3, message = "name must be min of 3 charactors!!")
    private String name;

    @Email(message = "email address is not valid")
    private String email;

    @NotEmpty
    @Size(min = 3, max = 10, message = "pass must be btw 3-10 chars")
    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$")
    private String password;

    @NotEmpty
    private String gender;

    @NotEmpty
    private String about;


    private String imageName;
}
