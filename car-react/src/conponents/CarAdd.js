import React, {useEffect, useState} from 'react';
import axios from 'axios';
import {useNavigate} from 'react-router-dom';
import useAsync from '../customHook/useAsync';

/*/////////////////////DB값 받아오기(카테고리,브랜드)/////////////////////*/
async function getCategory(id, maker) {
    const response = await axios.get("http://localhost:8081/car/category?category=" + id + "&maker=" + maker)
    console.log(response);
    return response.data;
}

/*/////////////////////AddCar 컴포넌트 함수 시작/////////////////////*/
function AddCar() {
    const [cateState, refetch] = useAsync(getCategory);
    // 카테고리 요청 상태 업데이트
    const {loading, data, error} = cateState;
    // 경로 이동을 위한 상태 업데이트
    const navigate = useNavigate();
    // 1) 카테고리 선택, 메이커 선택 값을 관리
    const [cate, setCate] = useState({
        category: 1,
        maker: 1
    })

    /*/////////////////////폼데이터 상태 관리/////////////////////*/
    const [formData, setFormData] = useState({
        //기본값 설정
        title: "",           //제목
        cardesc: "",        //상세설명  textarea
        color: "",         //색상
        registerNumber: "",  //등록번호
        year: "",            //년도
        price: "",          //가격 (만원단위 입력)
        dealerId: "",      //임시 딜러 아이디
        displacement: "",  //배기량
        mileage: "",       //주행거리
        transmission: "자동",  //변속기 select 자동/수동/CVT/듀얼클러치
        fuel: "전기",        //연료   select 전기/가솔린/수소/디젤/하이브리드/LPG
        modelId: "1"    //아직 수입차 모델이 없어서 초기값으로 준거 같은데

    })
    /*/////////////////////카테고리, 메이커(브랜드) 선택 병경했을때 실행/////////////////////*/
    const onCateChange = (e) => {
        //이벤트를 발생시킨 select의 name값과 value값 각각 변수에 할당
        const {name, value} = e.target;
        //상태 업데이트
        setCate({
            ...cate,
            [name]: value,
            maker: name != "category" ? value : value == 2 ? 6 : 1
        });

    }
    /*/////////////////////input(하나하나 정보)에 변화가 일어났을 때 상태 업데이트/////////////////////*/
    const onChange = (e) => {
        const {name, value} = e.target;
        setFormData({
            ...formData,
            [name]: value
        })
    }
    /*/////////////////////Form 태그 생성해서 carformdata에 할당/////////////////////*/
    const carFormData = new FormData();

    /*/////////////////////파일타입(이미지)의 Input에 변경이 일어났을 때/////////////////////*/
    const onChangeImage = (e) => {
        const {name} = e.target;
        console.dir(e.target);
        // 파일이 업로드 되었을 때 나타내는 조건 - 이벤트가 발생한 Input의 files 속성이 있을 때 ㄴ
        if (e.target.files && e.target.files.length > 0) {
            // 폼태그에 input태그 속성 추가하기
            carFormData.append(name, e.target.files[0]);
        }
    }
    /*/////////////////////Form Data Reset/////////////////////*/
    const onReset = () => {
        setFormData({
            brand: "",
            model: "",
            color: "",
            registerNumber: "",
            year: "",
            price: "",
            dealerId: ""
        })
    }
    /*/////////////////////차량 저장정보 전달/////////////////////*/
    const onSubmit = (e) => {
        // 기존  전송 이벤트 제거
        e.preventDefault();
        // 입력이 완료되었는 지 확인
        carInsert();

    }
    /*/////////////////////폼테그 제출 데이터 - 차량정보 저장/////////////////////*/
    async function carInsert() {
        // 폼에 전달한 속성을 다 추가(input(name, value))
        carFormData.append("title", formData.title);
        carFormData.append("mileage", formData.mileage);
        carFormData.append("color", formData.color);
        carFormData.append("registerNumber", formData.registerNumber);
        carFormData.append("year", formData.year);
        carFormData.append("price", formData.price);
        carFormData.append("dealerId", sessionStorage.getItem("dealerId"));
        console.log("sadsadsadsadsa"+ sessionStorage.getItem("dealerId"))
        carFormData.append("displacement", formData.displacement);
        carFormData.append("cardesc", formData.cardesc);
        carFormData.append("transmission", formData.transmission);
        carFormData.append("modelId", formData.modelId);
        carFormData.append("fuel", formData.fuel);
        carFormData.append("categoryId", cate.category);
        carFormData.append("makerId", cate.maker);

        const token = sessionStorage.getItem("jwt");
        //axios.post(경로,전송데이터,옵션(객체 경태))
        //이제 권한이 필요한 작업에서는 토큰을 가지고 가야 한다.
        try {
            // 로그인 성공 시 받은 토큰은 세션스토리지(브라우저 저장소)에 저장
            const response = await axios.post("http://localhost:8081/dealer/addCar", carFormData, {
                //확인정보 요청정보들은 헤더에 가지고 안다.
                //carFormData DB에 저장해야하는 정보들은 body에 가지고 간다.
                //중고차 등록페이지로 이동하는 걸 막은게 아니라 차동차 등록 버튼 무르는 걸 막은것
                headers: {
                    "Content-Type": "multipart/form-data",
                    "Authorization" : token
                }
            });
            console.log(response);
            if (response.data === "ok") {
                navigate("/");
            }
        } catch (e) {
            console.log(e);
        }
    }
    /*/////////////////////가테고리,브랜드 변경시 리패치/////////////////////*/
    useEffect(() => {
        refetch(cate);
    }, [cate]);

    if (loading) return <div>로딩중 ....</div>
    if (error) return <div> 에러가 발생했습니다.</div>
    if (!data) return <div> 데이터가 없습니다.</div>

    /*/////////////////////리턴/////////////////////*/
    return (
        <div>
            <h2>차량 등록 하기</h2>
            <form onSubmit={onSubmit}>
                {/*/////////////////////카테고리/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="category" className="form-label">카테고리</label>
                    <select name="category" id="category" onChange={onCateChange}
                            value={cate.category} className="form-control">
                        {data.categories.map(li => (
                            <option value={li.id} key={li.id}>{li.categoryName}</option>
                        ))}
                    </select>
                </div>
                {/*/////////////////////브랜드/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="maker" className="form-label">브랜드</label>
                    <select name="maker" id="maker" className="form-control"
                            onChange={onCateChange} value={cate.maker}>
                        {data.makers.map(li => (
                            <option value={li.id} key={li.id}>{li.makerName}</option>
                        ))}
                    </select>
                </div>
                {/*/////////////////////모델/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="model" className="form-label">모델</label>
                    <select name="modelId" id="model" className="form-control"
                            onChange={onChange} value={formData.modelId}>
                        {data.models.map(li => (
                            <option value={li.id} key={li.id}>{li.modelName}</option>
                        ))}
                    </select>
                </div>
                {/*/////////////////////제목/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="title" className="form-label">제목</label>
                    <input type="text"
                           name="title"
                           value={formData.title}
                           onChange={onChange} className="form-control"
                           id="title" aria-describedby="titleHelp"
                    />
                </div>
                {/*/////////////////////설명글/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="cardesc" className="form-label">설명글</label>
                    <textarea type="text"
                              name="cardesc"
                              value={formData.cardesc}
                              onChange={onChange} className="form-control"
                              id="cardesc"
                    ></textarea>
                </div>
                {/*/////////////////////배기량/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="displacement" className="form-label">배기량</label>
                    <input type="text"
                           name="displacement"
                           value={formData.displacement}
                           onChange={onChange} className="form-control"
                           id="displacement" aria-describedby="displacementlHelp"
                    />
                </div>
                {/*/////////////////////색상/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="color" className="form-label">색상</label>
                    <input type="text"
                           name="color"
                           value={formData.color}
                           onChange={onChange} className="form-control"
                           id="color" aria-describedby="colorHelp"
                    />
                </div>
                {/*/////////////////////등록번호/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="registerNumber" className="form-label">등록번호</label>
                    <input type="text"
                           name="registerNumber"
                           value={formData.registerNumber}
                           onChange={onChange} className="form-control"
                           id="registerNumber" aria-describedby="registerNumberHelp"
                    />
                </div>
                {/*/////////////////////가격/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="price" className="form-label">가격</label>
                    <input type="text"
                           name="price"
                           placeholder='만원단위로 입력하세요'
                           value={formData.price}
                           onChange={onChange} className="form-control"
                           id="price" aria-describedby="priceHelp"
                    />
                </div>
                {/*/////////////////////연식/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="year" className="form-label">년도</label>
                    <input type="text"
                           name="year"
                           value={formData.year}
                           onChange={onChange} className="form-control"
                           id="year" aria-describedby="yearHelp"
                    />
                </div>
                {/*/////////////////////주행거리/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="mileage" className="form-label">주행거리</label>
                    <input type="text"
                           name="mileage"
                           value={formData.mileage}
                           onChange={onChange} className="form-control"
                           id="mileage" aria-describedby="mileageHelp"
                    />
                </div>
                {/*/////////////////////변속기/////////////////////*/}
                <div className="mb-3">
                    <label htmlFor="transmission" className="form-label">변속기</label>
                    <select
                        name="transmission"
                        value={formData.transmission}
                        onChange={onChange} className="form-control"
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
                        value={formData.fuel}
                        onChange={onChange} className="form-control"
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
                <div className="mb-3">
                    <label htmlFor="year" className="form-label">이미지1</label>
                    <input type='file' className='custom-file-input form-control'
                           name='uploadFiles' onChange={onChangeImage}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="year" className="form-label">이미지2</label>
                    <input type='file' className='custom-file-input form-control'
                           name='uploadFiles' onChange={onChangeImage}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="year" className="form-label">이미지3</label>
                    <input type='file' className='custom-file-input form-control'
                           name='uploadFiles' onChange={onChangeImage}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="year" className="form-label">이미지4</label>
                    <input type='file' className='custom-file-input form-control'
                           name='uploadFiles' onChange={onChangeImage}/>
                </div>
                <div className="mb-3">
                    <label htmlFor="year" className="form-label">이미지5</label>
                    <input type='file' className='custom-file-input form-control'
                           name='uploadFiles' onChange={onChangeImage}/>
                </div>
                <div>
                    <button className="btn btn-primary" type="submit">등록</button>
                    <button className="btn btn-primary" type="reset" onClick={onReset}>취소</button>
                </div>
            </form>
        </div>
    );
}

export default AddCar;