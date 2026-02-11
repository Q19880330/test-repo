import axios from 'axios';
import React from 'react';
import useAsync from '../customHook/useAsync';
import {Link} from "react-router-dom";

//전달할 함수
async function getProducts(id){
    const response = await axios.get('http://localhost:8081/car/cars?categoryId='+id);
    return response.data;
}
function CarList({id}) {
    //{loading:false,data:null,error:null}
    const [state] = useAsync(getProducts,id);
    const { loading, data, error } = state;
    if (loading) return <div>로딩중....</div>;
    if (error) return <div>에러가 발생했습니다.</div>;
    if (!data) return null;
    return (
        <div className="mainlist">
            {data.dtoList.map((car, index) =>
                <div className="card" style={{width: "18rem"}} key={index}>
                    <img src={"http://localhost:8081/car/image?image=" + car.imgName} width={200} className="card-img-top"
                         alt="..."/>
                    <div className="card-body">
                        <h5 className="card-title">{car.title}</h5>
                        <p className="card-text">{car.makerName}</p>
                        <Link to={"/carDetail/" + car.id} className="btn btn-primary">{car.price}만원</Link>
                    </div>
                </div>
            )}
        </div>
    );
}

export default CarList;