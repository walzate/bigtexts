package co.edu.javeriana.bigtexts.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
@Entity
@Table(name = "BIGTEXTS_FILE")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BigtextsFile.findAll", query = "SELECT b FROM BigtextsFile b"),
    @NamedQuery(name = "BigtextsFile.findById", query = "SELECT b FROM BigtextsFile b WHERE b.id = :id"),
    @NamedQuery(name = "BigtextsFile.findByName", query = "SELECT b FROM BigtextsFile b WHERE b.name = :name"),
    @NamedQuery(name = "BigtextsFile.findByWeight", query = "SELECT b FROM BigtextsFile b WHERE b.weight = :weight")})
public class BigtextsFile implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "WEIGHT")
    private Long weight;
    @JoinColumn(name = "ID_EXECUTION_LOG", referencedColumnName = "ID")
    @ManyToOne(fetch = FetchType.EAGER)
    private ExecutionLog idExecutionLog;

    public BigtextsFile() {
    }

    public BigtextsFile(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getWeight() {
        return weight;
    }

    public void setWeight(Long weight) {
        this.weight = weight;
    }

    public ExecutionLog getIdExecutionLog() {
        return idExecutionLog;
    }

    public void setIdExecutionLog(ExecutionLog idExecutionLog) {
        this.idExecutionLog = idExecutionLog;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof BigtextsFile)) {
            return false;
        }
        BigtextsFile other = (BigtextsFile) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.bigtexts.entities.BigtextsFile[ id=" + id + " ]";
    }
    
}
