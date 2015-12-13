package biblioteca;

import com.google.gson.annotations.Expose;
import excecao.ExcecaoSistema;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotBlank;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author MASC
 */
@Embeddable
public class ArquivoDigital implements Serializable {
    @NotNull
    @Lob
    @Basic(fetch = FetchType.LAZY, optional = true)
    @Column(name = "BLOB_ARQUIVO", nullable = true)
    private byte[] arquivo;
    @NotNull    
    @ExtensaoDocumento
    @Column(name = "TXT_EXTENSAO_ARQUIVO", nullable = true)
    private String extensao;
    @NotBlank
    @Expose
    @Column(name = "TXT_NOME_ARQUIVO", nullable = true)
    private String nome;

    public byte[] getArquivo() {
        return arquivo;
    }

    public void setArquivo(byte[] arquivo) {
        this.arquivo = arquivo;
    }

    public String getExtensao() {
        return extensao;
    }

    public void setExtensao(String extensao) {
        this.extensao = extensao;
    }

    public String getNome() {
        String nomeCodificado = null;
        
        try {
            nomeCodificado = URLEncoder.encode(this.nome, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new ExcecaoSistema(ex);
        }
        return nomeCodificado;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    public StreamedContent getFile() {
        InputStream stream = new ByteArrayInputStream(arquivo);
        return new DefaultStreamedContent(stream, extensao, nome);
    }
}
