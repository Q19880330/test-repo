import React, {useState} from "react";
import axios from "axios";
import {useNavigate, useParams} from "react-router-dom";
import useAsync from "../customHook/useAsync";

async function getCar(id) {
    const response = await axios.get("http://localhost:8081/car/car/" + id);

    return response.data;
}

function DealerCarEdit() {
    const {carId} = useParams()
    const [state] = useAsync(getCar, carId);
    // 경로 이동을 위한 상태 업데이트
    const navigate = useNavigate();
    const {loading, data, error} = state;

    /*/////////////////////차량 저장정보 전달/////////////////////*/
    const onSubmit = (e) => {
        // 기존  전송 이벤트 제거
        e.preventDefault();
        console.log(e.target);//form자체
        // 입력이 완료되었는 지 확인
        carInsert(e.target);

    }

    /*/////////////////////폼테그 제출 데이터 - 차량정보 저장/////////////////////*/
    async function carInsert(form) {
        const token = sessionStorage.getItem("jwt");
        //axios.post(경로,전송데이터,옵션(객체 경태))
        //이제 권한이 필요한 작업에서는 토큰을 가지고 가야 한다.
        try {
            // 로그인 성공 시 받은 토큰은 세션스토리지(브라우저 저장소)에 저장
            const response = await axios.post("http://localhost:8081/dealer/carEdit", form, {
                //확인정보 요청정보들은 헤더에 가지고 안다.
                //carFormData DB에 저장해야하는 정보들은 body에 가지고 간다.
                //중고차 등록페이지로 이동하는 걸 막은게 아니라 차동차 등록 버튼 무르는 걸 막은것
                headers: {
                    "Content-Type": "multipart/form-data",
                    "Authorization": token
                }
            });
            console.log(response);
            if (response.data === "ok") {
                alert("변경됨")
                navigate("/dealerList");
            }
        } catch (e) {
            console.log(e);
        }
    }/////////
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    if(loading) return <div>로딩중....</div>;
    if(error) return <div>에러가 발생했습니다.</div>;
    if(!data) return null;

    return (
        <div>
            <h2>차량 수정 하기</h2>
            <form onSubmit={onSubmit}>
                <input type="hidden" name="id" value={carId}/>
                {/*/////////////////////카테고리/////////////////////*/}
                {/* <div className="mb-3">
                    <label htmlFor="category" className="form-label">카테고리</label>
                    <select name="category" id="category" onChange={onCateChange}
                            value={cate.category} className="form-control">

                            <option></option>

                    </select>
                </div>*/}
                {/*/////////////////////브랜드/////////////////////*/}
                {/*  <div className="mb-3">
                    <label htmlFor="maker" className="form-label">브랜드</label>
                    <select name="maker" id="maker" className="form-control"
                            onChange={onCateChange} value={cate.maker}>

                        <option></option>

                    </select>
                </div>*/}
                {/*/////////////////////모델/////////////////////*/}
                {/*<div className="mb-3">
                    <label htmlFor="model" className="form-label">모델</label>
                    <select name="modelId" id="model" className="form-control"
                             value={formData.modelId}>

                        <option></option>

                    </select>
                </div>*/}
                {/*/////////////////////제목/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="title" className="form-label">제목</label>
                    <input type="text" defaultValue={data.title}
                           name="title" className="form-control"
                           id="title" aria-describedby="titleHelp"
                    />
                </div>
                {/*/////////////////////설명글/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="cardesc" className="form-label">설명글</label>
                    <textarea type="text"
                              name="cardesc"
                              defaultValue={data.cardesc}
                              className="form-control"
                              id="cardesc"
                    ></textarea>
                </div>
                {/*/////////////////////배기량/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="displacement" className="form-label">배기량</label>
                    <input type="text"
                           name="displacement"
                           defaultValue={data.displacement}
                           className="form-control"
                           id="displacement" aria-describedby="displacementlHelp"
                    />
                </div>
                {/*/////////////////////색상/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="color" className="form-label">색상</label>
                    <input type="text"
                           name="color"
                           defaultValue={data.color}
                           className="form-control"
                           id="color" aria-describedby="colorHelp"
                    />
                </div>
                {/*/////////////////////등록번호/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="registerNumber" className="form-label">등록번호</label>
                    <input type="text"
                           name="registerNumber"
                           defaultValue={data.registerNumber}
                           className="form-control"
                           id="registerNumber" aria-describedby="registerNumberHelp"
                    />
                </div>
                {/*/////////////////////가격/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="price" className="form-label">가격</label>
                    <input type="text"
                           name="price"
                           placeholder='만원단위로 입력하세요'
                           defaultValue={data.price}
                           className="form-control"
                           id="price" aria-describedby="priceHelp"
                    />
                </div>
                {/*/////////////////////연식/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="year" className="form-label">년도</label>
                    <input type="text"
                           name="year"
                           defaultValue={data.year}
                           className="form-control"
                           id="year" aria-describedby="yearHelp"
                    />
                </div>
                {/*/////////////////////주행거리/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="mileage" className="form-label">주행거리</label>
                    <input type="text"
                           name="mileage"
                           defaultValue={data.mileage}
                           className="form-control"
                           id="mileage" aria-describedby="mileageHelp"
                    />
                </div>
                {/*/////////////////////변속기/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="transmission" className="form-label">변속기</label>
                    <select
                        name="transmission"
                        defaultValue={data.transmission}
                        className="form-control"
                        id="transmission" aria-describedby="mileageHelp"
                    >
                        <option value="자동">자동</option>
                        <option value="수동">수동</option>
                        <option value="CVT">CVT</option>
                        <option value="듀얼클러치">듀얼클러치</option>
                    </select>
                </div>
                {/*/////////////////////연료/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="fuel" className="form-label">연료</label>
                    <select
                        name="fuel"
                        defaultValue={data.fuel}
                        className="form-control"
                        id="fuel" aria-describedby="fuelHelp"
                    >
                        <option value="전기">전기</option>
                        <option value="가솔린">가솔린</option>
                        <option value="수소">수소</option>
                        <option value="디젤">디젤</option>
                        <option value="하이브리드">하이브리드</option>
                        <option value="LPG">LPG</option>
                    </select>
                </div>
                {/*/////////////////////이미지/////////////////////*/}
                {/* {data.carImageDtos.map((img,index)=> (
                    <div className="mb-3">
                        <label htmlFor="year" className="form-label">이미지{index+1}</label>
                        <input type='file' className='custom-file-input form-control'
                               name='uploadFiles' onChange={onChangeImage}/>
                    </div>
                ))}*/}
                <div>
                    <button className="btn btn-primary" type="submit">등록</button>
                    <button className="btn btn-primary" type="reset">취소</button>
                </div>
            </form>
        </div>
    );
}

export default DealerCarEdit;