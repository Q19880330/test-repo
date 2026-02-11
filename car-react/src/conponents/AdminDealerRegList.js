import React from "react";
import axios from "axios";
import useAsync from "../customHook/useAsync";

async function getDealer() {
    const response = await axios.get("http://localhost:8081/admin/dealerRegList",{
        headers : {
            "Authorization" : sessionStorage.getItem("jwt")
        }
    });
    return response.data;
}
function AdminDealerRegList(){
    const [state] = useAsync(getDealer);
    const {loading, data, error} = state;
    const addDealer = async (e) => {
        const dataset = e.target.dataset;
        const response = await axios.post("http://localhost:8081/admin/dealerAdd",JSON.stringify(dataset),{
            headers : {
                "Authorization" : sessionStorage.getItem("jwt")
            }
        })
        if(response.data==="ok"){
            alert("딜러등록 되었습니다.")
        }else {
            alert("문제가 발생")
        }
    }

    if (loading) return <div>로딩중......</div>;
    if (error) return <div>에러 발생</div>;
    if (!data) return null;
    return (
        <div>
            <h3>딜러요청목록</h3>
            <table className="table">
                <tr>
                    <th scope='col'>아이디</th>
                    <th scope='col'>이름</th>
                    <th scope='col'>전화번호</th>
                    <th scope='col'>지역</th>
                    <th scope='col'>메세지</th>
                    <th scope='col'>멤버 아이디</th>
                </tr>
                {data.map(dealer =>
                    <tr>
                        <td>{dealer.id}</td>
                        <td>{dealer.name}</td>
                        <td>{dealer.phone}</td>
                        <td>{dealer.location}</td>
                        <td>{dealer.message}</td>
                        <td>{dealer.memberId}
                            <button
                            data-name={dealer.id}
                            data-phone ={dealer.phone}
                            data-location={dealer.location}
                            data-memberId={dealer.memberId}
                            onClick={addDealer}
                        >승인</button>
                        </td>
                    </tr>
                )}
            </table>
        </div>
    );

}

export default AdminDealerRegList;