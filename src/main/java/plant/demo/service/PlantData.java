package plant.demo.service;

import com.mysql.cj.jdbc.NClob;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "plant")
public class PlantData {

    @Id
    @Column(length = 10)
    private String cntntsNo;
    @Column(length = 100)
    private String cntntsSj;
    @Column(length = 100)
    private String plntbneNm;   //식물학명
    @Column(columnDefinition = "TEXT")
    private String plntzrNm;    //식물영명
    @Column(length = 100)
    private String distbNm;     //유통명
    @Column(length = 100)
    private String fmlNm;       //과명

    private String orgplceInfo; //원산지 정보
    @Column(columnDefinition = "LONGTEXT")
    private String adviseInfo;  //조언 정보

    private String growthHgInfo;    //성장높이 정보

    private String growthAraInfo;   //성장넓이 정보
    @Column(length = 10)
    private String smellCode;   //냄새 코드

    private String toxctyInfo;  //독성 정보
    @Column(length = 10)
    private String managelevelCode;  //관리수준 코드
    @Column(length = 10)
    private String grwtveCode;  //생장속도 코드
    @Column(length = 10)
    private String grwhTpCode;  //생육온도 코드
    @Column(length = 10)
    private String winterLwetTpCode;    //겨울 최저온도 코드
    @Column(length = 10)
    private String hdCode;      //습도 코드

    private String frtlzrInfo;  //비료 정보
    @Column(length = 10)
    private String watercycleSprngCode;     //물주기 봄 코드
    @Column(length = 10)
    private String watercycleSummerCode;    //물주기 여름 코드
    @Column(length = 10)
    private String watercycleAutumnCode;    //물주기 가을 코드
    @Column(length = 10)
    private String watercycleWinterCode;    //물주기 겨울 코드
    @Column(columnDefinition = "LONGTEXT")
    private String speclmanageInfo;     //특별관리 정보
    @Column(columnDefinition = "LONGTEXT")
    private String fncltyInfo;          //기능성 정보
    @Column(length = 10)
    private String managedemanddoCode;  //관리요구도 코드

    //getter, setter

    public String getCntntsNo() {
        return cntntsNo;
    }

    public void setCntntsNo(String cntntsNo) {
        if (cntntsNo == null){
            this.cntntsNo = UUID.randomUUID().toString();
        }
        else {
            this.cntntsNo = cntntsNo;
        }
    }

    public String getCntntsSj() {
        return cntntsSj;
    }

    public void setCntntsSj(String cntntsSj) {
        this.cntntsSj = cntntsSj;
    }

    public String getPlntbneNm() {
        return plntbneNm;
    }

    public String getPlntzrNm() {
        return plntzrNm;
    }

    public String getDistbNm() {
        return distbNm;
    }

    public String getFmlNm() {
        return fmlNm;
    }

    public String getOrgplceInfo() {
        return orgplceInfo;
    }

    public String getAdviseInfo() {
        return adviseInfo;
    }

    public String getGrowthHgInfo() {
        return growthHgInfo;
    }

    public String getGrowthAraInfo() {
        return growthAraInfo;
    }

    public String getSmellCode() {
        return smellCode;
    }

    public String getToxctyInfo() {
        return toxctyInfo;
    }

    public String getManagelevelCode() {
        return managelevelCode;
    }

    public String getGrwtveCode() {
        return grwtveCode;
    }

    public String getGrwhTpCode() {
        return grwhTpCode;
    }

    public String getWinterLwetTpCode() {
        return winterLwetTpCode;
    }

    public String getHdCode() {
        return hdCode;
    }

    public String getFrtlzrInfo() {
        return frtlzrInfo;
    }

    public String getWatercycleSprngCode() {
        return watercycleSprngCode;
    }

    public String getWatercycleSummerCode() {
        return watercycleSummerCode;
    }

    public String getWatercycleAutumnCode() {
        return watercycleAutumnCode;
    }

    public String getWatercycleWinterCode() {
        return watercycleWinterCode;
    }

    public String getSpeclmanageInfo() {
        return speclmanageInfo;
    }

    public String getFncltyInfo() {
        return fncltyInfo;
    }

    public String getManagedemanddoCode() {
        return managedemanddoCode;
    }

    public void printData(){
        System.out.println(this.cntntsNo);
        System.out.println(this.adviseInfo);
        System.out.println(this.cntntsSj);
        System.out.println(this.distbNm);
        System.out.println(this.fmlNm);
        System.out.println(this.fncltyInfo);
        System.out.println(this.frtlzrInfo);
        System.out.println(this.growthAraInfo);
        System.out.println(this.growthHgInfo);
        System.out.println(this.grwhTpCode);
        System.out.println(this.grwtveCode);
        System.out.println(this.hdCode);
        System.out.println(this.managedemanddoCode);
        System.out.println(this.managelevelCode);
        System.out.println(this.orgplceInfo);
        System.out.println(this.plntbneNm);
        System.out.println(this.plntzrNm);
        System.out.println(this.smellCode);
        System.out.println(this.speclmanageInfo);
        System.out.println(this.toxctyInfo);
        System.out.println(this.watercycleAutumnCode);
        System.out.println(this.watercycleSprngCode);
        System.out.println(this.watercycleWinterCode);
        System.out.println(this.watercycleSummerCode);
        System.out.println(this.winterLwetTpCode);
    }
}
