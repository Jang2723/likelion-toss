## API 사용해보기 - Toss Payment
- https://developers.tosspayments.com/
- 연동키 확인
  - 내 개발 정보 확인 메뉴
- [토스 페이먼츠 개발자 센터](https://developers.tosspayments.com/sandbox)

1. 사용자가 주문을 진행하는 페이지로 이동합니다.
2. Toss Payments SDK를 이용해 HTML에 결제 UI를 그려줍니다.
3. 사용자는 결제 UI를 이용해 결제를 진행합니다.
4. 이후 설정에 따라,
    1. JavaScript의 Promise 형태로 결제 승인 이후의 과정을 처리합니다.
    2. 결제 요청을 하면서 제공한 successUrl로 클라이언트가 Redirect 됩니다.

   이 과정에서 저희 백엔드 서버로 결제 정보가 전달되어야 합니다.

5. 백엔드 서버에서 전달받은 결제 정보를 바탕으로, Toss Payments에 결제 승인 요청을 합니다. 이 과정이 생략될 경우 실제 결제가 이뤄지지 않습니다.
6. 결제 결과를 사용자에게 다시 알려줍니다.

- 실제로 백엔드에서 진행하는 내용은 많지 않다. 
- 특히 결제를 진행하는 사용자 정보, 결제 대상 품목 등을 포함하고 싶다면, 승인 요청을 위해 JavaScript에서 결제 정보를 전달할 때 함께 전달해야 한다.


- [코어 API | 토스 페이먼츠 개발자센터](https://docs.tosspayments.com/reference)

---
## 간단 요구사항
- 주어진 Skeleton 코드는 몇개의 품목들에 대해서 결제를 진행할 수 있는 간단한 서비스
- Template 엔진 없이 순수한 HTML과 JavaScript, Promise 기반으로 동작
- 사용자가 결제를 완료하면 서버의 `/toss/confirm-payment`로 결제 정보를 전달
- 주문되는 물품의 ID와 이름을 `<ID>-<이름>`형식으로 연결해, `orderName`으로 지정되 결제 요청이 진행
  - 나중에 API를 이용해 결제 정보를 조회할 때 사용 가능
- [결제 위젯 JavaScript SDK](https://docs.tosspayments.com/reference/widget-sdk#requestpayment%EA%B2%B0%EC%A0%9C-%EC%A0%95%EB%B3%B4)
---
- 전달받은 결제 정보를 바탕으로 결제 승인 요청을 Toss로 보냅니다.
- 어떤 물품이 결제되었는지, 해당 물품의 Toss 결제 정보는 무엇인지가 포함된 주문 정보를 데이터베이스에 저장합니다.
- 결제가 진행되었던 주문 정보들을 전체, 또는 단일 조회가 가능합니다.
- 결제가 진행되었던 주문 정보들의 Toss 결제 정보에 대한 개별 조회가 가능합니다.
- 결제가 진행되었던 주문 정보들의 결제에 대하여, 개별 취소가 가능합니다.

## 사용할 API
이 프로젝트에서 활용할 API

### 기본 인증
- Toss에서는 API를 위해 Basic Authentication을 사용
- 특정 ID와 key 등의 정보를 `Authorization` 헤더에 Base64 형식으로 인코등해 첨부하는 방법
- Toss에서는 시크릿 키를 `<시크릿 키>:`의 형식으로 정리 후 해당 값을 Base64로 인코딩 할 것을 요구
- https://docs.tosspayments.com/reference/using-api/authorization


### 결제 승인 요청
- Client에서 UI를 이용해 결제를 진행한 뒤, 백엔드에서 해당 결제의 승인을 요청하기 위한 API
- https://docs.tosspayments.com/reference#%EA%B2%B0%EC%A0%9C-%EC%8A%B9%EC%9D%B8
```Bash
curl --request POST \
  --url https://api.tosspayments.com/v1/payments/confirm \
  --header 'Authorization: Basic dGVzd...==' \
  --header 'Content-Type: application/json' \
  --data '{"paymentKey":"5zJ4xY7m...PaZdL6","orderId":"a4CWyWY5m89PNh7xJwhk1","amount":15000}'
```
`POST /v1/payments/confirm`

Request Body   
- `paymentKey`: paymentKey 문자열
- `orderId`: orderId 문자열
- `amount`: 결제 금액


- 응답으로는 결제에 대한 상세한 정보를 담고 있는 [Payment객체](https://docs.tosspayments.com/reference#payment-객체)가 반환
- 이중 특히 `paymentKey`와 `orderId`의 경우 추후 이 정보를 다시 조회하기 위해 필요한 정보로서 따로 저장해야 함


### `paymentKey`로 결제 조회
- 결제할 때 생성된 `paymentKey`로 Toss의 결제 관련 정보를 확인하는 API
- https://docs.tosspayments.com/reference#paymentkey%EB%A1%9C-%EA%B2%B0%EC%A0%9C-%EC%A1%B0%ED%9A%8C
```Bash
curl --request GET \
  --url https://api.tosspayments.com/v1/payments/5zJ4xY7m...PaZdL6 \
  --header 'Authorization: Basic dGVzd...=='
```
`GET /v1/payments/{paymentKey}`   

Path Variable   
- `paymentKey`: paymentKey 문자열

응답으로는 앞선 결제 승인과 마찬가지로 결제에 대한 상세한 정보를 담고있는 [Payment 객체](https://docs.tosspayments.com/reference#payment-객체)가 반환

### `orderId`로 결제 조회
- 결제할 대 점주(즉 개발자)가 생성한 `orderId`로 Toss의 결제 관련 정보를 확인하는 API
- https://docs.tosspayments.com/reference#orderid%EB%A1%9C-%EA%B2%B0%EC%A0%9C-%EC%A1%B0%ED%9A%8C
```Bash
curl --request GET \
  --url https://api.tosspayments.com/v1/payments/orders/a4CWyWY5m89PNh7xJwhk1 \
  --header 'Authorization: Basic dGVzd...=='
```
`GET /v1/payments/orders/{orderId}`

응답으로는 앞선 결제 승인과 마찬가지로 결제에 대한 상세한 정보를 담고있는 [Payment 객체](https://docs.tosspayments.com/reference#payment-객체)가 반환


### 결제 취소
- `paymentKey`를 가지고 승인된 결제를 취소
- https://docs.tosspayments.com/reference#%EA%B2%B0%EC%A0%9C-%EC%B7%A8%EC%86%8C
```Bash
curl --request POST \
  --url https://api.tosspayments.com/v1/payments/5zJ4xY7m...PaZdL6/cancel \
  --header 'Authorization: Basic dGVzd...==' \
  --header 'Content-Type: application/json' \
  --data '{"cancelReason":"고객 변심"}'
```
`POST /v1/payments/{paymentKey}/cancel`

Path Variable
- `paymentKey`: paymentKey 문자열

Request Body
- `cancelReason`: 결제를 취소하는 이유

응답으로는 앞선 결제 승인과 마찬가지로 결제에 대한 상세한 정보를 담고있는 [Payment 객체](https://docs.tosspayments.com/reference#payment-객체)가 반환