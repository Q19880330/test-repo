import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

function Join({ setAuth }) {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        name: "",
        email: "",
        password: "",
        address: ""
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
            username: "",
            password: "",
        })
    }
    const onSubmit = (e) => {
        //form에 연결된 이벤트를 제거
        e.preventDefault();
        //입력이 다 되어있는지 체크
        if (formData.name && formData.password && formData.email && formData.address) {
            memberJoin();
        }
    }

    async function memberJoin() {
        try {
            const response = await axios.post("http://localhost:8081/site/join", formData)
            //ok or fail
            console.log(response);
            if(response.data==="ok"){
                navigate("/login");
            }
        } catch (e) {
            console.log(e);
        }


    }
    return (
        <div>
            <h2>회원가입</h2>
            <form onSubmit={onSubmit}>
                <div className="mb-3">
                    <label htmlFor="exampleInputEmail1" className="form-label">이름</label>
                    <input type="text"
                           name="name"
                           value={formData.name}
                           onChange={onChange} className="form-control"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="exampleInputEmail1" className="form-label">이메일</label>
                    <input type="text"
                           name="email"
                           value={formData.email}
                           onChange={onChange}
                           className="form-control"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="exampleInputEmail1" className="form-label">패스워드</label>
                    <input type="password"
                           name="password"
                           value={formData.password}
                           onChange={onChange}
                           className="form-control"/>
                </div>
                <div className="mb-3">
                    <label htmlFor="exampleInputEmail1" className="form-label">주소</label>
                    <input type="text"
                           name="address"
                           value={formData.address}
                           onChange={onChange}
                           className="form-control"/>
                </div>
                <div>
                    <button type="submit" className="btn btn-primary">회원가입</button>
                    <button type="reset" className="btn btn-primary" onClick={onReset}>취소</button>
                </div>
            </form>
        </div>
    );
}

export default Join;