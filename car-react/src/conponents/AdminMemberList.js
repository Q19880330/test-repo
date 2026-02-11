import React from "react";
import axios from "axios";
import useAsync from "../customHook/useAsync";

async function getMember() {
    const response = await axios.get("http://localhost:8081/admin/memberList",{
        headers : {
            "Authorization" : sessionStorage.getItem("jwt")
        }
    });
    return response.data;
}
function AdminMemberList(){
    const [state] = useAsync(getMember);
    const {loading, data, error} = state;

    if (loading) return <div>로딩중......</div>;
    if (error) return <div>에러 발생</div>;
    if (!data) return null;
    return (
        <div>
            <h3>회원목록</h3>
            <table className="table">
                <tr>
                    <th scope='col'>이름</th>
                    <th scope='col'>이메일</th>
                    <th scope='col'>주소</th>
                    <th scope='col'>권한</th>
                    <th scope='col'>딜러아이디</th>
                </tr>
                {data.map(member => (
                    <tr>
                        <td>{member.name}</td>
                        <td>{member.email}</td>
                        <td>{member.address}</td>
                        <td>{member.role}</td>
                        <td>{member.dealerId}</td>
                    </tr>
                ))}
            </table>
        </div>
    );

}

export default AdminMemberList;