package com.green.car.service;

import com.green.car.dto.*;
import com.green.car.entity.*;
import com.green.car.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService{
    private final CarRepository carRepository;
    private final DealerRepository dealerRepository;
    private final CarImageService carImageService;
    private final MakerRepository makerRepository;
    private final CategoryRepository categoryRepository;
    private final ModelRepository modelRepository;


    //차량정보 등록
    @Override
    public void addCar(CarDto dto, List<MultipartFile> carImgFileList) throws Exception {
        //입력받은 값으로 car객체 대입
        Car car = dtoToEntity(dto);
        //엔티티 타입의 필드는 입력받은 값으로 조회 후 setter로 대입
        Dealer dealer = dealerRepository.findById(dto.getDealerId()).get();
        //카테고리,메커, 모델
        Category category = categoryRepository.findById(dto.getCategoryId()).get();
        Maker maker = makerRepository.findById(dto.getMakerId()).get();
        Model model = modelRepository.findById(dto.getModelId()).get();
        car.setDealer(dealer);
        car.setCategory(category);
        car.setMaker(maker);
        car.setModel(model);
        carRepository.save(car);
        //이미지 등록
        for (int i = 0; i < carImgFileList.size(); i++) {
            //CarImageEntity 생성
            //CarImageDto로는 안함...
            CarImage carImage = new CarImage();
            //연관관계 생성
            carImage.setCar(car);
            //첫번째 이미지를 대표이미지로 지정
            if (i==0){
                carImage.setRepImgYn("Y");
            }else{
                carImage.setImgName("N");
            }
            //itemImgService 호출
            carImageService.saveCarImage(carImage, carImgFileList.get(i));
        }
        //return car.getId();
    }

    @Override
    public Long carRemove(Long id) {
        return null;
    }

    @Override
    public CarDto findById(Long id) {
        //1. car조회
        Car car = carRepository.findById(id).get();
        CarDto dto = new CarDto();
        dto.update(
                car.getCategory().getId(),
                car.getModel().getId(),
                car.getMaker().getId(),
                car.getDealer().getId(),
                car.getId(),
                car.getRegisterNumber(),
                car.getTransmission(),
                car.getFuel(),
                car.getTitle(),
                car.getCardesc(),
                car.getColor(),
                car.getYear(),
                car.getPrice(),
                car.getDisplacement(),
                car.getMileage()
                );
        //2. carId에 맞는 CarImage조회
        List<CarImageDto> result = carImageService.findByCarId(id);
        dto.setCarImageDtos(result);
        //3. Dealer정보
        dto.setDealer(car.getDealer());
        return dto;
    }


    //메인 항목 조회(베스트 수입차, 베스트 국산차)
    @Override
    public ResultDto<MainCarDto, Object[]> carMainlist(RequestDto dto) {
        //Pageable 객체 생성
        Pageable pageable = dto.getPageable(Sort.by("id"));
        Page<Object[]> result = carRepository.getCarMainList(pageable,dto.getCategoryId());
        Function<Object[], MainCarDto> fn = (arr->{
            return entityToObjectDto((Car) arr[0],(CarImage) arr[1]);
        });
        return new ResultDto<>(result,fn);
    }


    //목록페이지 상품조회
    @Override
    public List<ListCarDto> getpageList(Long id, String maker,String model) {
        //id가 10이면 전체 조회 그 외는 해당 카테고리로 조회
        List<Object[]> result = new ArrayList<>();
        /*List<Object[]> result = id==10? carRepository.getCarList() : carRepository.getCarMainList(id);*/

        //경우의 수
        //maker값은 제조사, model 값은 모델 (초기값)
        //maker값이 제조사가 아니고 model값이 모델일때 (제조사만 받은경우)
        //getListSearchMaker(maker)
        //maker값이 제조사가 아니고 model값도 모델이 아닐때 (제조사와 모델명 모두 받은 경우)
        //getListSearchMakerModel(maker,model)
        //id가 10이 아닐때
        /*if (id!=10){
            result = carRepository.getCarMainList(id);
        } else if (!maker.equals("제조사") && model.equals("모델")){
            result = carRepository.getListSearchMaker(maker);
        } else if(!maker.equals("제조사") && !model.equals("모델")){
            result = carRepository.getListSearchMakerModel(maker, model);
        } else if (id ==10 && maker.equals("제조사") && model.equals("모델")) {
            result =  carRepository.getCarList();
        }*/

        //id가 10일때 (전체자동차 조회(크린카 매장 메뉴))
        if(id==10) {
            if (maker.equals("제조사") && model.equals("모델")) {
                result =  carRepository.getCarList();
            }
            //(제조사만 받은경우)
            else if (!maker.equals("제조사") && model.equals("모델")){
                result = carRepository.getListSearchMaker(maker);
            }
            //(제조사와 모델명 모두 받은 경우)
            else if (!maker.equals("제조사") && !model.equals("모델")) {
                result =  carRepository.getListSearchMakerModel(maker, model);
            }
        }
        //국산차, 수입차 조회 (국산차, 수입차 메뉴)
        else {
            //항목 검색전
            if (maker.equals("제조사") && model.equals("모델")) {
                result =  carRepository.getCarPageList(id);
            }
            //(제조사만 받은경우)
            else if (!maker.equals("제조사") && model.equals("모델")){
                result = carRepository.getListSearchMaker(maker);
            }
            //(제조사와 모델명 모두 받은 경우)
            else if (!maker.equals("제조사") && !model.equals("모델")) {
                result =  carRepository.getListSearchMakerModel(maker, model);
            }
        }



        List<ListCarDto> listCarDto = new ArrayList<>();
        for (Object[] obj : result){
            Car car = (Car) obj[0];
            CarImage carImage = (CarImage) obj[1];
            ListCarDto dto = ListCarDto.builder()
                    .id(car.getId())
                    .year(car.getYear())
                    .title(car.getTitle())
                    .price(car.getPrice())
                    .imgName(carImage.getImgName())
                    .fuel(car.getFuel())
                    .dealer(car.getDealer())
                    .mileage(car.getMileage())
                    .build();
            listCarDto.add(dto);
        }
        return listCarDto;
    }

    @Override
    public List<MainCarDto> getCarList() {
        List<Object[]> result =carRepository.getCarList();
        List<MainCarDto> carDtoList = new ArrayList<>();
        for (Object[] obj : result){
            MainCarDto dto = entityToObjectDto((Car) obj[0],(CarImage) obj[1]);
            carDtoList.add(dto);
        }
        return carDtoList;
    }

    //대분류, 중분류,소분류 카테고리 조회
    @Override
    public CategoryDto getCategory(Long categoryId, Long makerId ){
        //categoryId 1 또는 2일때는 국산차, 스입차 항목조회
        // 그 외에는 모든 항목조회
        CategoryDto result = new CategoryDto();
        List<Category> categories = categoryRepository.findAll();
        List<Maker> makers = (categoryId ==1 || categoryId==2) ?
                makerRepository.getList(categoryId) :
                makerRepository.findAll();
        //makerId 0이면 모든 항목조회 아닐때는 해당하는 id만 조회
        List<Model> models = makerId == 0 ?
                modelRepository.findAll() :
                modelRepository.getModelList(makerId);
        result.setCategories(categories);
        result.setMakers(makers);
        result.setModels(models);
        return result;
    }

    //딜러 아이디 가지고 해당 딜러의 등록 자동차 출력
    @Override
    public List<ListCarDto> getDealercarList(Long dealerId) {
        List<Object[]> result = carRepository.getDealeridList(dealerId);
        List<ListCarDto> listCarDto = new ArrayList<>();
        for (Object[] obj : result){
            Car car = (Car) obj[0];
            CarImage carImage = (CarImage) obj[1];
            ListCarDto dto = ListCarDto.builder()
                    .id(car.getId())
                    .year(car.getYear())
                    .title(car.getTitle())
                    .price(car.getPrice())
                    .imgName(carImage.getImgName())
                    .fuel(car.getFuel())
                    .dealer(car.getDealer())
                    .mileage(car.getMileage())
                    .build();
            listCarDto.add(dto);
        }
        return listCarDto;
    }

    @Override
    public void editCar(CarDto dto) {
        //update 엔티티 조회 ---> 엔티티 값 변경 -> repository.save(엔티티)
        Car car = carRepository.findById(dto.getId()).get();
        car.setCardesc(dto.getCardesc());
        car.setColor(dto.getColor());
        car.setFuel(dto.getFuel());
        car.setPrice(dto.getPrice());
        car.setTransmission(dto.getTransmission());
        car.setTitle(dto.getTitle());
        car.setYear(dto.getYear());
        car.setRegisterNumber(dto.getRegisterNumber());
        car.setMileage(dto.getMileage());
        car.setDisplacement(dto.getDisplacement());
        carRepository.save(car);

    }

}
