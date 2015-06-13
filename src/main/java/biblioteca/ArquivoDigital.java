package biblioteca;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author MASC
 */
@Embeddable
public class ArquivoDigital implements Serializable {
    @Lob
    @Basic(fetch = FetchType.LAZY, optional = true)
    @Column(name = "BLOB_ARQUIVO", nullable = true)
    private byte[] arquivo;    
    @ExtensaoDocumento
    @Column(name = "TXT_EXTENSAO_ARQUIVO", nullable = true)
    private String extensao;
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
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
    
    //TODO: Transformar em converter
    public StreamedContent getFile() {
        InputStream stream = new ByteArrayInputStream(arquivo);
        return new DefaultStreamedContent(stream, extensao, nome);
    }
}
