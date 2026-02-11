import axios from 'axios';
import React, {useEffect, useState} from 'react';
import {useNavigate} from 'react-router-dom';

function Login({setAuth, isAuthenticated}) {
    const navigate = useNavigate();
    const [formData, setFormData] = useState({
        username: "",
        password: "",
    })
    //input에 변경이 일어났을때 상태 업데이트
    const onChange = (e) => {
        const {name, value} = e.target;
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
        if (formData.username && formData.password) {
            memberLogin();
        }
    }
    useEffect(() => {
        //로그인 마운트 될때 로그인 했는지 체크해서
        //로그인 했을 경우 메인페이지로 이동
        if(isAuthenticated){
            navigate("/");
        }
    }, []);

    async function memberLogin() {
        try {
            const response = await axios.post("http://localhost:8081/site/login", formData)
            //로그인 성공시 받은 토큰을 세션스토리지(브라우저 저장소)에 저장
            const jwtToken = response.data.grantType + " " + response.data.accessToken;
            //받아온 토큰을 브라우저 세션스토리지에 저장
            sessionStorage.setItem("jwt", jwtToken);
            sessionStorage.setItem("email", response.data.email);
            sessionStorage.setItem("role", response.data.role);
            sessionStorage.setItem("dealerId", response.data.dealerId);
            sessionStorage.setItem("memberId", response.data.memberId);
            //로그인하면 true로 변환
            setAuth(true);
            //메인페이지 이동
            navigate("/");
        } catch (e) {
            console.log(e);
        }


    }

    return (
        <div>
            <h2>로그인하기</h2>
            <form onSubmit={onSubmit}>
                <div className="mb-3">
                    <label htmlFor="email" className="form-label">이메일</label>
                    <input type="text" className="form-control"
                           id="email" placeholder="메일주소를 입력하세요"
                           name="username"
                           value={formData.username}
                           onChange={onChange}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="password" className="form-label">패스워드</label>
                    <input type="password" className="form-control"
                           id="password" placeholder="패스워드를 입력하세요"
                           name="password"
                           value={formData.password}
                           onChange={onChange}/>
                </div>
                <div>
                    <button type="submit" className="btn btn-primary">로그인</button>
                    <button type="reset" onClick={onReset} className="btn btn-primary">취소</button>
                </div>
            </form>
        </div>
    );
}

export default Login;