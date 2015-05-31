package biblioteca;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.FetchType;
import javax.persistence.Lob;

/**
 *
 * @author MASC
 */
@Embeddable
public class ArquivoDigital implements Serializable {
    @Lob
    @Basic(fetch = FetchType.LAZY, optional = true)
    @Column(name = "ARQUIVO", nullable = true)
    private byte[] arquivo;    
    @ExtensaoDocumento
    @Column(name = "TXT_EXTENSAO", nullable = true)
    private String extensao;

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
}
