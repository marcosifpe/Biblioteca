/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servico;

import static javax.ejb.TransactionAttributeType.SUPPORTS;
import static javax.ejb.TransactionManagementType.CONTAINER;

import java.util.concurrent.Future;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.AsyncResult;
import javax.ejb.Asynchronous;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionManagement;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 *
 * @author MASC
 */
@Stateless
@LocalBean
@TransactionManagement(CONTAINER)
@TransactionAttribute(SUPPORTS)
public class EmailServico {
    @Resource(name = "mail/scSession")
    private Session sessao;
    
    @Asynchronous
    public void enviarMensagem(String para) {
        enviar(para);
    }
    
    @Asynchronous
    public Future<Boolean> enviarEmail(String para) {
        return new AsyncResult<>(enviar(para));
    }
    
    private boolean enviar(String para) {
        try {
            Message mensagem = new MimeMessage(sessao);
            mensagem.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(para));
            mensagem.setSubject("Cadastro realizado com sucesso");
            mensagem.setText("Cadastro realizado com sucesso. E-mail automático.");
            Transport.send(mensagem);
            return true;
        } catch (MessagingException ex) {
            Logger.getLogger(EmailServico.class.getName()).log(Level.SEVERE, ex.getMessage(), ex);
            return false;
        }
    }
}
