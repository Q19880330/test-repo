import './App.css';
import Header from "./conponents/header";
import {Route, Routes} from "react-router-dom";
import Login from "./conponents/Login";
import {useState} from "react";
import Join from "./conponents/Join";
import CarAdd from "./conponents/CarAdd";
import CarDetail from "./conponents/CarDetail";
import Main from "./conponents/Main";
import List from "./conponents/List";
import DealerReg from "./conponents/DealerReg";
import UserRoutes from "./conponents/UserRoutes";
import DealerRoutes from "./conponents/DealerRoutes";
import DealerCarList from "./conponents/DealerCarList";
import DealerCarEdit from "./conponents/DealerCarEdit";
import AdminRoutes from "./conponents/AdminRoutes";
import AdminMain from "./conponents/AdminMain";

function App() {
    //로그인 한 상태인지
    const [isAuthenticated, setAuth] = useState(false);
    const role = sessionStorage.getItem("role");
    return (
        <div className="App">
            <Header isAuthenticated={isAuthenticated} setAuth={setAuth} role={role}/>
            <div className="container">
                <Routes>
                    <Route path='/' element={<Main/>}/>
                    <Route path='/carList/:categoryId' element={<List/>}/>
                    <Route path="/login" element={<Login isAuthenticated={isAuthenticated} setAuth={setAuth}/>}/>
                    <Route element={<DealerRoutes role={role}/>}>
                        <Route path="/addCar" element={<CarAdd/>}/>
                        <Route path="/dealerList" element={<DealerCarList/>}/>
                        <Route path='/carEdit/:carId' element={<DealerCarEdit/>}></Route>
                    </Route>
                    <Route path="/join" element={<Join/>}/>
                    <Route path="/carDetail/:carId" element={<CarDetail/>}/>
                    <Route element={<UserRoutes role={role}/>}>
                        <Route path="/dealer" element={<DealerReg/>}/>
                    </Route>
                    <Route element={<AdminRoutes role={role}/>}>
                        <Route path="/admin" element={<AdminMain/>}/>
                    </Route>
                </Routes>
            </div>
        </div>
    );
}

export default App;
