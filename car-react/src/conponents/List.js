import React, {useEffect, useState} from "react";
import {useParams} from "react-router-dom";
import CarPageList from "./CarPageList";
import Category from "./Category";

function List (){
    //param객체 주소창 :  /page/10 --> 라우터 설정  <Route path = "/page/:categoryId"> {categoryId : 10}
    const { categoryId } = useParams();//param객체 리턴
    const [keyword, setKeyword] = useState({
        maker : "제조사",
        model : "모델"
    });
    const onReset = () => {
        setKeyword({
            maker : "제조사",
            model : "모델"
        })
    }
    //const {maker, model} = keyword;
    //구조분해 할당 하거나 프롭스 보낼때 꺼내서보내야함
    const  onSearch = (state) => {
        setKeyword({
            ...keyword,
            ...state
        })
    }
    useEffect(() => {
        onReset();
    }, [categoryId]);

    return(
        <div>
            <Category keyword={keyword} onSearch={onSearch} categoryId={categoryId}/>
            <CarPageList categoryId={categoryId} maker={keyword.maker} model={keyword.model}/>
        </div>
    );

}
export default List;