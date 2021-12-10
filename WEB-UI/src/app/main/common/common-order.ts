export class CommonOrder {
  paymentMethod = [
    { key: 0, value: 'COD' },
    { key: 1, value: 'ZaloPay' }
  ];

  getTotal(list: any[]) {
    let total = 0;
    list.map((value: any) => {
      total += value.price;
    });
    return total;
  }

  getReceivedOrder(received: number) {
    return received == -1
      ? { style: 'color: #FF4D4F', icon: 'close', value: 'Đã hủy' }
      : received == 1
        ? { style: 'color: #1890FF', icon: 'inbox', value: 'Đang giao' }
        : received == 2
          ? { style: 'color: #52c41a', icon: 'check', value: 'Đã giao' }
          : { style: 'color: #FF4D4F', icon: 'car', value: 'Chờ xác thực' };
  }
}
