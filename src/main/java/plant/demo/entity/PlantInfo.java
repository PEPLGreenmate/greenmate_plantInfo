package plant.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

import java.util.UUID;

@Entity
@Getter
public class PlantInfo {

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
    @Column(length = 50)
    private String clCodeNm;
    @Column(length = 50)
    private String grwhstleCodeNm;
    @Column(length = 50)
    private String indoorpsncpacompositionCodeNm;
    @Column(length = 50)
    private String eclgyCodeNm;
    @Column(length = 50)
    private String lefmrkCodeNm;
    @Column(length = 50)
    private String lefcolrCodeNm;
    @Column(length = 50)
    private String ignSeasonCodeNm;
    @Column(length = 50)
    private String flclrCodeNm;
    @Column(length = 50)
    private String fmldeSeasonCodeNm;
    @Column(length = 50)
    private String fmldecolrCodeNm;
    @Column(length = 50)
    private String prpgtmthCodeNm;
    @Column(length = 100)
    private String lighttdemanddoCodeNm;
    @Column(length = 255)
    private String postngplaceCodeNm;
    @Column(length = 50)
    private String dlthtsCodeNm;

    public void setCntntsNo(String cntntsNo) {
        if (cntntsNo == null) {
            this.cntntsNo = UUID.randomUUID().toString();
        }
        else {
            this.cntntsNo = cntntsNo;
        }
    }

    public void setCntntsSj(String cntntsSj) {
        this.cntntsSj = cntntsSj;
    }
}
