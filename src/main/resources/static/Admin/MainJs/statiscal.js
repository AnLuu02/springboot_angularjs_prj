app.controller("ctrlstatistical", function ($scope, $http) {
  $scope.confirm = [];

  $scope.load_all = function () {
    $http
      .get(`http://localhost:8080/statistical/confirm`)
      .then((res) => {
        $scope.confirm = res.data;
      })
      .catch((error) => {
        console.log("Error", error);
      });
  };
  $scope.load = function () {
    $http
      .get(`http://localhost:8080/statistical/confirms`)
      .then((res) => {
        $scope.confirms = res.data;
      })
      .catch((error) => {
        console.log("Error", error);
      });
  };
  $scope.xacnhan = function (orderid) {
    $http
      .get(`http://localhost:8080/statistical/confirm/${orderid}`)
      .then((resp) => {
        $scope.form = resp.data;
        var item = $scope.form;
        item.status = 2;
        console.log(item);

        $http
          .put(`http://localhost:8080/statistical/confirm/${orderid}`, item)
          .then((resp) => {
            Swal.fire("Hệ Thống", "Xác nhận đơn hàng thành công!!", "success");
            $http
              .post(`http://localhost:8080/send/orders`, item)
              .then((ressendOder) => {
                console.log(ressendOder);
              })
              .catch((error) => {
                console.log(error);
                alert("that bai");
              });
            $scope.load();
            $scope.load_all();
          })
          .catch((error) =>
            Swal.fire("Error", "Hình như là mình hết hàng rùi :(", "error")
          );
      })
      .catch((error) => console.log("Error", error));
  };
  $scope.huydon = function (orderid) {
    $http
      .get(`http://localhost:8080/statistical/confirm/${orderid}`)
      .then((resp) => {
        $scope.form = resp.data;
        var item = $scope.form;
        item.status = 3;
        console.log(item);

        $http
          .put(`http://localhost:8080/statistical/confirm/${orderid}`, item)
          .then((resp) => {
            Swal.fire("Hệ Thống", "Đơn Hàng đã được hủy!", "success");
            $http
              .post(`http://localhost:8080/send/orders`, item)
              .then((ressendOder) => {
                console.log(ressendOder);
              })
              .catch((error) => {
                console.log(error);
                alert("that bai");
              });
            $scope.load();
            $scope.load_all();
          })
          .catch((error) => Swal.fire("Error", "Xác nhận rùi mà :(", "error"));
      })
      .catch((error) => console.log("Error", error));
  };

  $scope.info = function (orderid) {
    $http
      .get(`http://localhost:8080/statistical/infoDetail/${orderid}`)
      .then((resp) => {
        $scope.items = resp.data;
      })
      .catch((error) => console.log("Error", error));
  };

  $scope.top5item = function () {
    $http
      .get(`http://localhost:8080/statistical/top5items`)
      .then((resp) => {
        $scope.top5item = resp.data;
      })
      .catch((error) => console.log("Error", error));
  };

  $scope.top5buyer = function () {
    $http
      .get(`http://localhost:8080/statistical/top5buyer`)
      .then((resp) => {
        $scope.top5buyer = resp.data;
      })
      .catch((error) => console.log("Error", error));
  };

  $scope.top5ChartItem = function () {
    $http
      .get(`http://localhost:8080/statistical/turnoverday`)
      .then((resp) => {
        $scope.itemChart = resp.data;
        const labels = $scope.itemChart.map((item) => item[0]);
        const data = $scope.itemChart.map((item) => item[1]);

        const ctx = document
          .getElementById("top5ChartItemCanvas")
          .getContext("2d");
        new Chart(ctx, {
          type: "pie",
          data: {
            labels: labels,
            datasets: [
              {
                label: "Top 5 sản phẩm bán chạy",
                data: data,
                backgroundColor: [
                  "rgba(255, 99, 132, 0.2)",
                  "rgba(54, 162, 235, 0.2)",
                  "rgba(255, 206, 86, 0.2)",
                  "rgba(75, 192, 192, 0.2)",
                  "rgba(153, 102, 255, 0.2)",
                  "rgba(255, 159, 64, 0.2)"
                ],
                borderColor: [
                  "rgba(255, 99, 132, 1)",
                  "rgba(54, 162, 235, 1)",
                  "rgba(255, 206, 86, 1)",
                  "rgba(75, 192, 192, 1)",
                  "rgba(153, 102, 255, 1)",
                  "rgba(255, 159, 64, 1)"
                ],
                borderWidth: 1
              }
            ]
          },
          options: {
            responsive: true,
            plugins: {
              legend: {
                position: "top"
              },
              tooltip: {
                enabled: true
              }
            }
          }
        });
      })
      .catch((error) => console.log("Error", error));
  };
  $scope.top5ChartBuyer = function () {
    $http
      .get(`http://localhost:8080/statistical/top5buyer`)
      .then((resp) => {
        $scope.itemChart = resp.data;

        const labels = resp.data.map((item) => `${item[1]} (${item[0]})`);
        const data = resp.data.map((item) => item[3]);
        console.log(labels, data);

        const ctx = document
          .getElementById("top5ChartBuyerCanvas")
          .getContext("2d");
        new Chart(ctx, {
          type: "line",
          data: {
            labels: labels,
            datasets: [
              {
                label: "Top 5 người mua nhiều nhất",
                data: data,
                backgroundColor: "rgba(75, 192, 192, 0.2)",
                borderColor: "rgba(75, 192, 192, 1)",
                borderWidth: 1
              }
            ]
          },
          options: {
            responsive: true,
            plugins: {
              legend: {
                position: "top"
              }
            },
            scales: {
              y: {
                beginAtZero: true
              }
            }
          }
        });
      })
      .catch((error) => console.log("Error", error));
  };
  $scope.turnover = function () {
    $http
      .get(`http://localhost:8080/statistical/turnoverday`)
      .then((resp) => {
        $scope.itemTurnover = resp.data;
      })
      .catch((error) => console.log("Error", error));
  };

  $scope.turnovermonth = function () {
    $http
      .get(`http://localhost:8080/statistical/turnovermonth`)
      .then((resp) => {
        $scope.itemTurnover = resp.data;
      })
      .catch((error) => console.log("Error", error));
  };

  $scope.turnoveryear = function () {
    $http
      .get(`http://localhost:8080/statistical/turnoveryear`)
      .then((resp) => {
        $scope.itemTurnover = resp.data;
      })
      .catch((error) => console.log("Error", error));
  };
  $scope.turnovers = {
    page: 0,
    size: 5,
    get items() {
      var start = this.page * this.size;
      return $scope.itemTurnover.slice(start, start + this.size);
    },
    get count() {
      return Math.ceil((1.0 * $scope.itemTurnover.length) / this.size);
    },
    first() {
      this.page = 0;
    },
    prev() {
      this.page--;
      if (this.page < 0) {
        this.last();
      }
    },
    next() {
      this.page++;
      if (this.page >= this.count) {
        this.first();
      }
    },
    last() {
      this.page = this.count - 1;
    }
  };
  $scope.listConfirm = {
    page: 0,
    size: 3,
    get items() {
      var start = this.page * this.size;
      return $scope.confirm.slice(start, start + this.size);
    },
    get count() {
      return Math.ceil((1.0 * $scope.confirm.length) / this.size);
    },
    first() {
      this.page = 0;
    },
    prev() {
      this.page--;
      if (this.page < 0) {
        this.last();
      }
    },
    next() {
      this.page++;
      if (this.page >= this.count) {
        this.first();
      }
    },
    last() {
      this.page = this.count - 1;
    }
  };

  $scope.Confirms = {
    page: 0,
    size: 5,
    get items() {
      var start = this.page * this.size;
      return $scope.confirms.slice(start, start + this.size);
    },
    get count() {
      return Math.ceil((1.0 * $scope.confirms.length) / this.size);
    },
    first() {
      this.page = 0;
    },
    prev() {
      this.page--;
      if (this.page < 0) {
        this.last();
      }
    },
    next() {
      this.page++;
      if (this.page >= this.count) {
        this.first();
      }
    },
    last() {
      this.page = this.count - 1;
    }
  };

  $scope.load_all();
  $scope.load();
  $scope.top5ChartItem();
  $scope.top5ChartBuyer();
  $scope.turnover();
  $scope.top5item();
  $scope.top5buyer();
});
