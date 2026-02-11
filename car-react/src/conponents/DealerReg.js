import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function DealerReg({ setAuth }) {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: "",
        phone: "",
        message: "",
        location: "",
        memberId: sessionStorage.getItem("memberId")
    })
    //input에 변경이 일어났을때 상태 업데이트
    const onChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value
        })
    }
    const onReset = () => {
        setFormData({
            name: "",
            phone: "",
            message: "",
            location: ""
        })
    }
    //등록버튼 클릭시
    const onSubmit = (e) => {
        //form에 연결된 이벤트를 제거
        e.preventDefault();
        //입력이 다 되어있는지 체크
        if (formData.name && formData.phone && formData.location && formData.memberId ) {
            dealerRegister();
        }
    }

    async function dealerRegister() {
        const token = sessionStorage.getItem("jwt");
        //axios.post(경로,전송데이터,옵션(객체 경태))
        //이제 권한이 필요한 작업에서는 토큰을 가지고 가야 한다.
        try {
            const response = await axios.post("http://localhost:8081/member/register", formData, {
                headers : {
                    "Authorization" : token
                }
            });
            //ok or fail
            console.log(response);
            if(response.data==="ok"){
                navigate("/");
            }
        } catch (e) {
            console.log(e);
        }
    }
    return (
        <div>
            <h2>딜러 등록 신청하기</h2>
            <form onSubmit={onSubmit}>
                <div className="mb-3">
                    <label htmlFor="exampleInputEmail1" className="form-label">이름</label>
                    <input type="text"
                           name="name"
                           value={formData.name}
                           onChange={onChange} className="form-control"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="exampleInputEmail1" className="form-label">연락처</label>
                    <input type="text"
                           name="phone"
                           value={formData.phone}
                           onChange={onChange}
                           className="form-control"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="exampleInputEmail1" className="form-label">주소</label>
                    <input type="text"
                           name="location"
                           value={formData.location}
                           onChange={onChange}
                           className="form-control"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="exampleInputEmail1" className="form-label">요청사항</label>
                    <textarea name="message" value={formData.message}
                              onChange={onChange}
                              className="form-control">
                    </textarea>
                </div>
                <div>
                    <button type="submit" className="btn btn-primary">딜러신청</button>
                    <button type="reset" className="btn btn-primary" onClick={onReset}>취소</button>
                </div>
            </form>
        </div>
    );
}

export default DealerReg;