// fakeData.js
import restaurant from "../images/categoryMenu/restaurant.png";
import hospital from "../images/categoryMenu/hospital.png";
import market from "../images/categoryMenu/market.png";
import bank from "../images/categoryMenu/bank.png";
import school from "../images/categoryMenu/school.png";
import park from "../images/categoryMenu/national-park.png";
import hotel from "../images/categoryMenu/hotel.png";
import cafe from "../images/categoryMenu/coffee.png";
import cinema from "../images/categoryMenu/cinema.png";

const dataCategoryHanoi = [
  {
    icon: restaurant,
    text: "Restaurant",
    locations: [
      { name: "Quan An Ngon", lat: 21.028511, long: 105.835144 },
      { name: "Pizza 4P's", lat: 21.033364, long: 105.850643 },
      { name: "Cha Ca La Vong", lat: 21.035769, long: 105.846674 },
      { name: "Sen Tay Ho", lat: 21.072145, long: 105.818315 },
      { name: "Red Bean Restaurant", lat: 21.032054, long: 105.847784 },
    ],
  },
  {
    icon: market,
    text: "Market",
    locations: [
      { name: "Dong Xuan Market", lat: 21.036237, long: 105.848155 },
      { name: "Hom Market", lat: 21.024627, long: 105.84724 },
      { name: "Hang Da Market", lat: 21.029186, long: 105.84441 },
      { name: "Long Bien Market", lat: 21.044513, long: 105.84735 },
      { name: "Bac Qua Market", lat: 21.035689, long: 105.850083 },
    ],
  },
  {
    icon: hospital,
    text: "Hospital",
    locations: [
      { name: "Bach Mai Hospital", lat: 21.000089, long: 105.841183 },
      {
        name: "Vietnam National Children's Hospital",
        lat: 21.016275,
        long: 105.807479,
      },
      {
        name: "Hanoi Medical University Hospital",
        lat: 21.003019,
        long: 105.832101,
      },
      { name: "E Hospital", lat: 21.046889, long: 105.788377 },
      {
        name: "Vinmec International Hospital",
        lat: 21.047738,
        long: 105.863946,
      },
    ],
  },
  {
    icon: bank,
    text: "Bank",
    locations: [
      { name: "Vietcombank Tower", lat: 21.028511, long: 105.854101 },
      { name: "BIDV Tower", lat: 21.03096, long: 105.854563 },
      { name: "Techcombank Hoan Kiem", lat: 21.028663, long: 105.852117 },
      { name: "VPBank", lat: 21.029772, long: 105.845361 },
      { name: "Agribank", lat: 21.028841, long: 105.848527 },
    ],
  },
  {
    icon: school,
    text: "School",
    locations: [
      { name: "Hanoi-Amsterdam High School", lat: 21.009055, long: 105.797275 },
      { name: "Chu Van An High School", lat: 21.046641, long: 105.836048 },
      { name: "Nguyen Sieu School", lat: 21.030232, long: 105.841179 },
      { name: "TH School", lat: 21.019963, long: 105.810977 },
      { name: "Doan Thi Diem School", lat: 21.048841, long: 105.764949 },
    ],
  },
  {
    icon: park,
    text: "Park",
    locations: [
      { name: "Thong Nhat Park", lat: 21.015891, long: 105.845657 },
      { name: "Lenin Park", lat: 21.026871, long: 105.840545 },
      { name: "Yen So Park", lat: 20.971169, long: 105.875312 },
      { name: "Hoan Kiem Lake", lat: 21.028511, long: 105.852018 },
      { name: "Hoa Binh Park", lat: 21.071304, long: 105.778836 },
    ],
  },
  {
    icon: hotel,
    text: "Hotel",
    locations: [
      {
        name: "Sofitel Legend Metropole Hanoi",
        lat: 21.025657,
        long: 105.853564,
      },
      { name: "JW Marriott Hotel Hanoi", lat: 21.003558, long: 105.782983 },
      {
        name: "InterContinental Hanoi Westlake",
        lat: 21.055616,
        long: 105.825508,
      },
      { name: "Hanoi Daewoo Hotel", lat: 21.0302, long: 105.817101 },
      { name: "Lotte Hotel Hanoi", lat: 21.032431, long: 105.814544 },
    ],
  },
  {
    icon: cafe,
    text: "Cafe",
    locations: [
      { name: "Cafe Giang", lat: 21.033424, long: 105.847593 },
      { name: "Cong Caphe", lat: 21.035984, long: 105.849556 },
      { name: "The Note Coffee", lat: 21.028567, long: 105.85233 },
      { name: "Highlands Coffee", lat: 21.028494, long: 105.85122 },
      { name: "Loading T Café", lat: 21.033058, long: 105.847669 },
    ],
  },
  {
    icon: cinema,
    text: "Cinema",
    locations: [
      { name: "CGV Vincom Center", lat: 21.027154, long: 105.853411 },
      { name: "Lotte Cinema", lat: 21.033197, long: 105.814537 },
      { name: "BHD Star Cineplex", lat: 21.013708, long: 105.816451 },
      { name: "Galaxy Cinema", lat: 21.033557, long: 105.847796 },
      { name: "Platinum Cineplex", lat: 21.049604, long: 105.835288 },
    ],
  },
];

export default dataCategoryHanoi;
