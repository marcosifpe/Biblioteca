/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

/**
 *
 * @author MASC
 */
public class RespostaJson {
    private boolean sucesso;
    private String mensagem;

    public RespostaJson() {
        
    }
    
    public RespostaJson(boolean  sucesso, String mensagem) {
        setMensagem(mensagem);
        setSucesso(sucesso);
    }
    
    public boolean isSucesso() {
        return sucesso;
    }

    public void setSucesso(boolean sucesso) {
        this.sucesso = sucesso;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }
}
