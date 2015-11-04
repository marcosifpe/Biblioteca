/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package acesso;

import java.io.Serializable;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import sun.security.util.Password;

/**
 *
 * @author MASC
 */
@Entity
@Table(name = "tb_usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TXT_LOGIN")
    private String login;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 500)
    @Column(name = "TXT_PASSWORD")
    private String senha;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TXT_EMAIL")
    private String email;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "TXT_PRIMEIRO_NOME")
    private String primeiroNome;
    @Size(max = 45)
    @Column(name = "TXT_ULTIMO_NOME")
    private String ultimoNome;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "usuario")
    private List<Grupo> grupoList;

    @PrePersist
    public void gerarHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            digest.update(senha.getBytes(Charset.forName("UTF-8")));
            setSenha(Base64.getEncoder().encodeToString(digest.digest()));
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Usuario() {
        
    }
    
    public Usuario(String txtLogin) {
        this.login = txtLogin;
    }

    public Usuario(String txtLogin, String txtPassword, String txtEmail, String txtPrimeiroNome) {
        this.login = txtLogin;
        this.senha = txtPassword;
        this.email = txtEmail;
        this.primeiroNome = txtPrimeiroNome;
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

    public String getTxtEmail() {
        return email;
    }

    public void setTxtEmail(String txtEmail) {
        this.email = txtEmail;
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

    public List<Grupo> getGrupoList() {
        return grupoList;
    }

    public void setGrupoList(List<Grupo> grupoList) {
        this.grupoList = grupoList;
    }
    
    public void addGrupo(String nomeGrupo) {
        if (this.grupoList == null) {
            this.grupoList = new ArrayList<>();
        }
        
        Grupo grupo = new Grupo(this.login, nomeGrupo);
        this.grupoList.add(grupo);        
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
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
