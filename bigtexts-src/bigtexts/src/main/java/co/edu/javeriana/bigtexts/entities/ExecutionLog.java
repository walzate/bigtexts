package co.edu.javeriana.bigtexts.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * Entidad que almacena los datos de las ejecuciones
 *
 * @author Wilson Alzate Calderón <walzate@javeriana.edu.co>
 */
@Entity
@Table(name = "EXECUTION_LOG")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ExecutionLog.findAll", query = "SELECT e FROM ExecutionLog e"),
    @NamedQuery(name = "ExecutionLog.findById", query = "SELECT e FROM ExecutionLog e WHERE e.id = :id"),
    @NamedQuery(name = "ExecutionLog.findByInitialDate", query = "SELECT e FROM ExecutionLog e WHERE e.initialDate = :initialDate"),
    @NamedQuery(name = "ExecutionLog.findByFinalDate", query = "SELECT e FROM ExecutionLog e WHERE e.finalDate = :finalDate"),
    @NamedQuery(name = "ExecutionLog.findByTotalTime", query = "SELECT e FROM ExecutionLog e WHERE e.totalTime = :totalTime"),
    @NamedQuery(name = "ExecutionLog.findByUploadingDate", query = "SELECT e FROM ExecutionLog e WHERE e.uploadingDate = :uploadingDate"),
    @NamedQuery(name = "ExecutionLog.findByPipeline", query = "SELECT e FROM ExecutionLog e WHERE e.pipeline = :pipeline"),
    @NamedQuery(name = "ExecutionLog.findByNumberOfSlaves", query = "SELECT e FROM ExecutionLog e WHERE e.numberOfSlaves = :numberOfSlaves")})
public class ExecutionLog implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "INITIAL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date initialDate;
    @Column(name = "FINAL_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finalDate;
    @Column(name = "TOTAL_TIME")
    private Long totalTime;
    @Column(name = "UPLOADING_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date uploadingDate;
    @Column(name = "PIPELINE")
    private String pipeline;
    @Column(name = "NUMBER_OF_SLAVES")
    private Integer numberOfSlaves;
    @Column(name = "PREPROCESSING_TASKS")
    private String preprocessingTasks;
    @OneToMany(mappedBy = "idExecutionLog", fetch = FetchType.EAGER)
    private List<BigtextsFile> bigtextsFileList;
    @Column(name = "STATUS")
    private String status;

    public ExecutionLog() {
    }

    public ExecutionLog(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getInitialDate() {
        return initialDate;
    }

    public void setInitialDate(Date initialDate) {
        this.initialDate = initialDate;
    }

    public Date getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(Date finalDate) {
        this.finalDate = finalDate;
    }

    public Long getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Long totalTime) {
        this.totalTime = totalTime;
    }

    public Date getUploadingDate() {
        return uploadingDate;
    }

    public void setUploadingDate(Date uploadingDate) {
        this.uploadingDate = uploadingDate;
    }

    public String getPipeline() {
        return pipeline;
    }

    public void setPipeline(String pipeline) {
        this.pipeline = pipeline;
    }

    public Integer getNumberOfSlaves() {
        return numberOfSlaves;
    }

    public void setNumberOfSlaves(Integer numberOfSlaves) {
        this.numberOfSlaves = numberOfSlaves;
    }

    public String getPreprocessingTasks() {
        return preprocessingTasks;
    }

    public void setPreprocessingTasks(String preprocessingTasks) {
        this.preprocessingTasks = preprocessingTasks;
    }
    
    @XmlTransient
    @JsonIgnore
    public List<BigtextsFile> getBigtextsFileList() {
        return bigtextsFileList;
    }

    public void setBigtextsFileList(List<BigtextsFile> bigtextsFileList) {
        this.bigtextsFileList = bigtextsFileList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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
        if (!(object instanceof ExecutionLog)) {
            return false;
        }
        ExecutionLog other = (ExecutionLog) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "co.edu.javeriana.bigtexts.entities.ExecutionLog[ id=" + id + " ]";
    }
    
}
