//package br.iftm.exercicio03.request;
//
//import br.iftm.exercicio03.models.Email;
//import br.iftm.exercicio03.models.User;
//
//import java.io.Serializable;
//import java.util.Objects;
//
//public class UserEmailRequest implements Serializable {
//    private User user;
//    private Email email;
//
//    public UserEmailRequest() {
//    }
//
//    public UserEmailRequest(User user, Email email) {
//        this.user = user;
//        this.email = email;
//    }
//
//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
//
//    public Email getEmail() {
//        return email;
//    }
//
//    public void setEmail(Email email) {
//        this.email = email;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if(this == o) return true;
//        if(o == null || getClass() != o.getClass()) return false;
//        UserEmailRequest that = (UserEmailRequest) o;
//        return Objects.equals(user, that.user) && Objects.equals(email, that.email);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(user, email);
//    }
//
//    @Override
//    public String toString() {
//        return "UserEmailRequest{" +
//                "user=" + user +
//                ", email=" + email +
//                '}';
//    }
//}