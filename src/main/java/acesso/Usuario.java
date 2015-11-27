/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acesso;

import biblioteca.Entidade;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

/**
 *
 * @author MASC
 */
@Entity
@Table(name = "tb_usuario")
@NamedQueries({
    @NamedQuery(name = Usuario.USUARIOS, query = "SELECT u FROM Usuario u ORDER BY u.primeiroNome, u.ultimoNome"),
    @NamedQuery(name = Usuario.USUARIO_POR_LOGIN, query = "SELECT u FROM Usuario u WHERE u.login = ?1")
})
public class Usuario extends Entidade implements Serializable {
    public static final String USUARIO_POR_LOGIN = "UsuarioPorLogin";
    public static final String USUARIOS = "Usuarios";
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TXT_LOGIN")
    private String login;
    @Size(max = 45)
    @Column(name = "TXT_PASSWORD")
    private String senha;
    @Size(max = 45)
    @Column(name = "TXT_SAL")
    private String sal;
    @NotNull
    @Email
    @Size(max = 45)
    @Column(name = "TXT_EMAIL")
    private String email;
    @NotBlank
    @Size(max = 45)
    @Column(name = "TXT_PRIMEIRO_NOME")
    private String primeiroNome;
    @NotBlank
    @Size(max = 45)
    @Column(name = "TXT_ULTIMO_NOME")
    private String ultimoNome;
    
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "TB_USUARIO_GRUPO", joinColumns = {
        @JoinColumn(name = "ID_USUARIO")},
            inverseJoinColumns = {
                @JoinColumn(name = "ID_GRUPO")})
    private List<Grupo> grupos;

    public Usuario() {
    }

    @PrePersist
    public void gerarHash() {
        try {
            gerarSal();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            setSenha(sal + senha);
            digest.update(senha.getBytes(Charset.forName("UTF-8")));
            setSenha(Base64.getEncoder().encodeToString(digest.digest()));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void gerarSal() throws NoSuchAlgorithmException {
        SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
        byte[] randomBytes = new byte[32];
        secureRandom.nextBytes(randomBytes);
        setSal(Base64.getEncoder().encodeToString(randomBytes));
    }

    public Usuario(String txtLogin) {
        this.login = txtLogin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSal() {
        return sal;
    }

    public void setSal(String sal) {
        this.sal = sal;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getPrimeiroNome() {
        return primeiroNome;
    }

    public void setPrimeiroNome(String txtPrimeiroNome) {
        this.primeiroNome = txtPrimeiroNome;
    }

    public String getUltimoNome() {
        return ultimoNome;
    }

    public void setUltimoNome(String txtUltimoNome) {
        this.ultimoNome = txtUltimoNome;
    }
    
    public void adicionarGrupo(Grupo grupo) {
        if (this.grupos == null) {
            this.grupos = new ArrayList<>();
        }
        
        this.grupos.add(grupo);
    }
    
    public List<Grupo> getGrupos() {
        return this.grupos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "acesso.Usuario[ txtLogin=" + login + " ]";
    }

}
