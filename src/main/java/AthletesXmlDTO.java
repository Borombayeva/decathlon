import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name="athletes")
@XmlAccessorType(XmlAccessType.FIELD)
public class AthletesXmlDTO {

    @XmlElement(name="athlete")
    private List<AthleteXmlDTO> athleteList;

    public List<AthleteXmlDTO> getAthleteList() {
        return athleteList;
    }

    public void setAthleteList(List<AthleteXmlDTO> athleteList) {
        this.athleteList = athleteList;
    }
}
