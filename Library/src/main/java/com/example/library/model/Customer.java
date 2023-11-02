package com.example.library.model;


import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "customers",uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="customer_id")
    private Long id;

    private static final long OTP_VALID_DURATION = 5 * 60 * 1000;   // 5 minutes

    @Column(name = "one_time_password")
    private String oneTimePassword;

    @Column(name = "otp_requested_time")
    private Date otpRequestedTime;


    public boolean isOTPRequired() {
        if (this.getOneTimePassword() == null) {
            return false;
        }

        long currentTimeInMillis = System.currentTimeMillis();
        long otpRequestedTimeInMillis = this.otpRequestedTime.getTime();

        if (otpRequestedTimeInMillis + OTP_VALID_DURATION < currentTimeInMillis) {
            // OTP expires
            return false;
        }

        return true;
    }


    private String firstname;

    private String lastname;

    private String username;

//   private String email;

    private String password;

    private String address;

    private String mobilenumber;

    @ManyToMany(fetch= FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinTable(name="customer_roles",joinColumns = @JoinColumn(name = "customer_id",referencedColumnName = "customer_id"),
    inverseJoinColumns = @JoinColumn(name = "role_id",referencedColumnName = "role_id"))
    private Collection<Role>roles;

   private boolean is_activated;

//   private boolean deleted;

    @OneToOne(mappedBy = "customer",cascade = CascadeType.ALL)
  private ShoppingCart cart;

}
