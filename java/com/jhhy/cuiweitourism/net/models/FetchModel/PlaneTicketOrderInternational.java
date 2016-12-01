package com.jhhy.cuiweitourism.net.models.FetchModel;

/**
 * Created by jiahe008 on 2016/12/1.
 * 国际机票下单请求
 */
public class PlaneTicketOrderInternational {

// {"head":{"code":"Order_gjflyorder"}, "field":{
// "PolicyId":"1","PlatCode":"074","AccountLevel":"10","BalanceMoney":"2808",
// "CommitKey":"<![CDATA[QlpoOTFBWSZTWVmq%2BGgAFl1\/\/cAAABhAf\/\/\/P\/\/\/67\/v\r\n\/\/QQAEIAFAAIAAAAgEABAOAMv3ewU2ytAMVKUVBVEAAAAAAAA4yYJoZDIyMmhoA0GR\r\nhANBo0yGIaAHGTBNDIZGRk0NAGgyMIBoNGmQxDQAZJoppkJoyaM0QwTTI0GRkGQDC\r\nMgyBoAlPUkIyARiNTKeU\/UMU\/JJ6bSm0npPUB6gGmQAAOMmCaGQyMjJoaANBkYQDQa\r\nNMhiGgAVJCCBNNEyam0aNJiZqanohhRsgTENlB5QGnlLn9Tt9x5FPjK8D5fb3Gz5M\/YVNh\r\nc\/qZn8z1tuWbTJKJA5VVLTztkUeaZSFVwqIYjWHeQsD44xJG6K5ggiS29fQLtrUX%2Bw35\r\nkmJM6tCZkmaB7k3XRdHviSRs9TsukhakJ73t09K0stWt735VPLU8Ji3FiNFRW8MeE66XnU\r\n6mWVVlkeFnwuz1jcrUsVUsmyyLsS0tHNFr5lYr%2B%2B59Wcz1kWtu3ZzgcMpNrKRZeyt\r\n0S\/pql8YmOe50XrK5uttyhRSNKYs\/b8\/434uGmKD7d5Z4FOnloUb448fFlivE6Kr4HB2E2H\r\nCYZLbzMXWBziEiKu9JrynbnmnOePn79nyp0ZstCom1zy0di2TI3zK%2By%2B\/m2uWSzQ\r\nzM7aK44a5VWCsnq1sbXX243R4IGi5F6OmJth59FAhD0hWqVDAEURjXcjFKOQzZSvyRpvj\r\nbVsyYtWwZkuxYFxSS2umcOrPevWY%2BiKJafDGbm1ts2Z9%2BOPPVWMCps2Fk\/A6zKY\r\nZqaHRN6XMjoqVjlRVdemqtxya192kfo9qdSaTRUac3iz%2BFcFgkdNIiPqjtn2JSieibrcl3Hg\r\ngKVCIycDop9teOtZ0aVKsa4dHZv7czgzGutVhrgSxqs6Fss8m%2B\/IcXkShFZKjujEEkgUI\r\nZpNIBPBCNpDqhD4191\/El19KYhfFhZipHLis89TTM%2Bql0pPvxaF6PwWttVvta7m%2Bot\r\nMGuuDC\/AqzTRRbSu8VLZaW1UTCT0pytOVNm%2B29vxWpkZGwvatm6Sy2pqZZY3Yhj\r\nUaFaDA5rnAyOGkWimZtXq%2Bq95fMysytv5li\/35NMDW2n3ZcyTLfwtWN4rZsWtZjO\/C2j\r\nOpUS2GymTiyy04XzalyxdijdGVKqjPSwxNmu6%2B1nbhqktLqila0q62ha2s22vksUturbDF\r\nt1VjRW%2BBhSJubBDIBeJvbjvO3tBDq7zuMRxxSiqoitob0CiL%2BfjGsrFVYEBBBBBDk6d\r\nMmzr9WGPXJIIxMkg9x5eQwGGHiS1rFFiqKKV2dvPw%2Bl8Lcf5\/sLdCbDluN4UOWCgMF\r\nAYZVdZDMhkDhVREKoDCFQyOeWlVRYuUjvNT0RKKiYz27bpVKuMhZQuLjFW93g6Lf4kid\/\r\nv\/g6DKCeNFPZvqqoIRvkVGcyBkBBCoC5gPi4MjM\/Ucydh3exPt%2Bsy7D2a\/G8h2vMcOa\r\nKKqui1cs3cRUwmRKT3s1rqqqUtB6y0oFUDpAQkOA1eJVVRqqhFVaFlnuPF9NVwdr7NDQy\r\n%2BB88d9yko5Oj1w\/2LqFFlJTtqMTcWtgtChcxjIyGUuuLTuO45jjx43z21aSpaVRsF116VjJ\r\nVVAQFRFXq18oRoDRB2sa0UwHOGaTDDe3JNy1Er7PI8vWY75YuWVUvyLohuCDX5Y5iPA\r\nNwGoD9OEMuZaplcB1B5NsaXRF\/rK4itRG7rG5CSylGmICog8x4tnM7Zb9mocwBsD2sags\r\n0LyGskKAgh3yBIC2zNf2%2BYwWGYz5y\/bznqW%2BGY9JIkkmgvXlVVURwJJxEygICJ5%\r\n2B6DXYZG4iMWcaW24SSYa1UsWUpYdj8Tmyy6nVveUse\/0LTgfC0sYmukRrbLS4VUTIIS\r\nAgghIESSZcwve\/jqrlziafW\/cfttU%2Bbw083dHyHP8l6yTgkpISBBE3ATK1sqcUu8AbxSKo\r\nVayqrf6KEsedpRZlprJIt2%2BjGZe4xEGMiEN1lgcfSRHkSCp2ngao%2BD5ks9tKcvEdXx8x\r\nB2HCRcRzJEChHpJhO4uP4HSD8He2bx\/d%2B6FPoJOxPp9wqkpoyiRY3A5QnrQpzk6EHrj\r\nBqGw4nPHzJJsT2EXUVHOsPmeI%2Bo6zUc4Zo2v6D8Ts0V7zx5j6lD3myylIpSKZhZJZ%2\r\nBtteUwsqJ1POfyOZsRhFFJT6377N7VHvQZIzPMbkwuZyacdtQpTyt9y1ldNnpXl7sbmVlaV1\r\nDJemHArpyXZ7FS%2BdqzSU000ZmeL3NmxsZqxnV19VtBZlphgwylpsoqlpRZbTYRoUjM\r\nWihZFCpKqUqqr0K1U2mSTIoKjbVVhPGnd1IU\/wZqJuNxm72g2p52riWG0HA50c7nc6k5n\r\nKFEav1jihzCeOPxFFFRRUb02RT6F3JTgGRvkpMzjJzG4OYLvNzGCocx8rvHgD1D0w8zuH2\r\nmCzyJD9Jk9C6d6UoSpIeMKFidSeJeXoqlKssyF5JZMiRZ9%2BiT0uxKe1KTaIp5yzyhvJOK\r\nkp0xO5F5IeVEU4noMMgofJGx3hqlnU3m92lRKQpVVUOekVKUo4TeOoME3Np53akOtOhf\r\nxBkohtiHiqqkk8QtE5xOWzB9PX2VUpcHQqLHytyDI6TIzMxYzEe11p2GrrSckmhNslEmr5z\r\nndBvBmGwaShU6U9QyOkahifWWDnUdClrVVr7Boi6nnXl5SpTCgwjcUpi6OpkkLSRN6M4U\r\nmx\/JP33anebYk0FIopQ2KWCy56O2R7KktRagy4oOlqKfBtEs%2BQO5nOg6Ejdg8BO4cDy\r\nIpofEHxJk8xo3RDYUHAe2T3LB3JOPr6h0i7qQ8Z4EdyGBsD4HfoJl6BsLwjaVJPJQWHlGEW\r\nk7lIolCy0Da7Cx8yC8kbUOzVcGIyBoWPK9YsHOG5nJHqBTgahZDduYkwlw3LKP09XiOW\r\nm8p7iWWmDGCYT42ShLoos2kw7CZQxJwYLpaSJcuIpB6MpSS%2BXmuLHAosUZVJZUy\r\nqSYJlly%2BIs0GwLmr4IwLLBtuKWbS6ikoXwqPMbGsE9LMLssChRGdTRwNRZ5\/0WDcTU\r\n4obEidh7l5zknafGfOMpOLk9gdskk40OR2C0nSh0vPLtp6OiIcpOlzDqaBZrZJYd6ZDwLu3g\r\nSqLMWvYi1S1DZL3oWlrEslKUWVCwso63oIzHs9X7PF1fcec4P9ZVVSSH\/VSQkjyFRCH%2\r\nBZUhJPzKkif0pJJCH5n2FiQdBSEif%2BFH\/pQIJ\/YqQjzFRCRPyKiJA\/KoIiTb%2BVm8r\/7%\r\n2Bx%2BfI\/Myj4\/oPzH\/4u5IpwoSCzVfDQA]]>",
// "TravelType":"RT",
// "InterFlights":[
//   [
//     {"SegmentID":"0","SegmentType":"S1","TicketingCarrier":"NX","ArrivalDate":"2015-11-16","ArrivalTime":"15:55","BoardPoint":"PEK","BoardPointAT":"T3",
//      "Carrier":"NX","ClassCode":"S,S\/S,S","ClassRank":"E\/E","DepartureDate":"2015-11-16","DepartureTime":"12:10","FlightNo":"005","OffPoint":"MFM","OffPointAT":""},
//     {"SegmentID":"1","SegmentType":"S1","TicketingCarrier":"NX","ArrivalDate":"2015-11-16","ArrivalTime":"19:55","BoardPoint":"MFM","BoardPointAT":"",
//      "Carrier":"NX","ClassCode":"S,S\/S,S","ClassRank":"E\/E","DepartureDate":"2015-11-16","DepartureTime":"18:10","FlightNo":"882","OffPoint":"BKK","OffPointAT":""}
//   ],
//   [
//     {"SegmentID":"0","SegmentType":"S2","TicketingCarrier":"NX","ArrivalDate":"2015-11-18","ArrivalTime":"06:30","BoardPoint":"BKK","BoardPointAT":"",
//      "Carrier":"NX","ClassCode":"S,S\/S,S","ClassRank":"E\/E","DepartureDate":"2015-11-18","DepartureTime":"02:55","FlightNo":"879","OffPoint":"MFM","OffPointAT":""},
//     {"SegmentID":"1","SegmentType":"S2","TicketingCarrier":"NX","ArrivalDate":"2015-11-18","ArrivalTime":"10:10","BoardPoint":"MFM","BoardPointAT":"",
//      "Carrier":"NX","ClassCode":"S,S\/S,S","ClassRank":"E\/E","DepartureDate":"2015-11-18","DepartureTime":"07:10","FlightNo":"006","OffPoint":"PEK","OffPointAT":"T3"}
//   ]
// ],
// "Passengers":[
//   {"Name":"zhiyong","PsgType":"1","CardType":"2","CardNo":"E1301230122","MobilePhone":"13264349337",
//    "Fare":"2030","ShouldPay":"738","Sex":"M","BirthDay":"1980-03-07","Country":"CN"}
// ]}}

    private String PolicyId; //政策ID
    private String PlatCode; //
    private String AccountLevel; //
    private String BalanceMoney; //

    private String CommitKey; //
    private String TravelType; //traveltype 航程类型 OW（单程） RT（往返）
    private String InterFlights; //
    private String Passengers; //



}
