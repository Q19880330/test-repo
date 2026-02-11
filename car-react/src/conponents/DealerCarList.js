import React from "react";
import axios from "axios";
import useAsync from "../customHook/useAsync";
import {Link} from "react-router-dom";

//전달할 함수
async function getCars(dealerId) {
    const token = sessionStorage.getItem("jwt");
    //axios.get(경로, {옵션})
    //post, get 두개가 다름
    const response = await axios.get("http://localhost:8081/dealer/carList?dealerId=" + dealerId, {
        headers: {
            "Authorization": token
        }
    })
    return response.data;
}

function DealerCarList() {
    const dealerId = sessionStorage.getItem("dealerId");
    const [state] = useAsync(getCars, dealerId)
    const {loading, data, error} = state;
    if (loading) return <div>로딩중 ....</div>
    if (error) return <div> 에러가 발생했습니다.</div>
    if (!data) return <div> 데이터가 없습니다.</div>
    return (
        <div>
            <h4>등록 {data.length}대</h4>
            <table className="table">
                <thead>
                <tr>
                    <th scope="col">사진</th>
                    <th scope="col">차량정보</th>
                    <th scope="col">연식</th>
                    <th scope="col">연료</th>
                    <th scope="col">주행거리</th>
                    <th scope="col">가격</th>
                    <th scope="col">딜러</th>
                </tr>
                </thead>
                <tbody>
                {
                    data.map((car, index) => (
                        <tr key={index}>
                            <td><img src={"http://localhost:8081/car/image?image=" + car.imgName}
                                     width="160px"/></td>
                            <td><Link to={"/carEdit/" + car.id}>{car.title}</Link></td>
                            <td>{car.year}</td>
                            <td>{car.fuel}</td>
                            <td>{car.mileage}</td>
                            <td>{car.price}</td>
                            <td>{car.dealer.name}</td>
                        </tr>
                    ))
                }

                </tbody>
            </table>
        </div>
    );
}

export default DealerCarList;