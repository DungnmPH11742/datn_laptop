Create database shop_laptop

Go

use shop_laptop
Create table account(
	id int identity(1,1) primary key not null,
	full_name nvarchar(100),
	phone varchar(11),
	email varchar(150),
	password varchar(255),
	date_of_birth date,
	verification_code varchar(64),
	time_token datetime,
	actived bit,
)

<<<<<<< HEAD
Create PROCEDURE  filter_Sales
as
Select * from products join sale_product on products.on_sale = sale_product.sale_code
where sale_product.status like 1 and (products.on_sale is not null or on_sale not like ' ') and  GETDATE() between sale_product.date_on and sale_product.date_off and 
Go

exec filter_Sales
Select * from products join sale_product on products.on_sale = sale_product.sale_code
where sale_product.status like 1 and (products.on_sale is not null or on_sale not like ' ') and  GETDATE() between sale_product.date_on and sale_product.date_off
=======

>>>>>>> main
/*------------------------------------------------------BẢNG ACCOUNT-----------------------------------------------------------------------------*/
insert into account(full_name, phone, email,password,date_of_birth, actived)
values ('Nam','0374563546','admin@gmail.com','$2a$12$KCCdTXZKuNEVPW4.lgjYUeK/qo5eUbfQkobauR3Nh4i7d.HLB9.3S','2010-09-08',null,null,1)
insert into account(full_name, phone, email,password,date_of_birth, actived)
values ('Hằng','0374656354','user@gmail.com','$2a$12$KCCdTXZKuNEVPW4.lgjYUeK/qo5eUbfQkobauR3Nh4i7d.HLB9.3S','2003-06-02',null,null,1)
/*-----------------------------------------------------------------------------------------------------------------------------------*/
insert into account(full_name, phone, email,password,date_of_birth, actived)
values ('hu','0374656354','user@gmail.com','$2a$12$KCCdTXZKuNEVPW4.lgjYUeK/qo5eUbfQkobauR3Nh4i7d.HLB9.3S','2003-06-02',null,null,1)

Create table role(
	id int identity(1,1) primary key,
	role_name varchar(25),
)

/*---------------------------------------------BẢNG ROLE--------------------------------------------------------------------------------------*/
Select * from role
insert into role values ('ROLE_USER'), ('ROLE_ADMIN')
/*-----------------------------------------------------------------------------------------------------------------------------------*/

Create table account_role(
	id_account int not null,
	id_role int not null,
	primary key(id_account, id_role),
)
--Khóa ngoại bảng account role
ALTER TABLE account_role ADD FOREIGN KEY (id_account) REFERENCES account(id);	

ALTER TABLE account_role ADD FOREIGN KEY (id_role) REFERENCES role(id);	


/*-------------------------------------------------BẢNG ACCOUNT_ROLE----------------------------------------------------------------------------------*/
insert into account_role values (1,2), (2,1)
/*-----------------------------------------------------------------------------------------------------------------------------------*/
Select * from products join sale_product on products.on_sale = sale_product.sale_code
where sale_product.status like 1 and (products.on_sale is not null or on_sale not like ' ') and  GETDATE() between sale_product.date_on and sale_product.date_off
Select * from products join products_detail on products.id = products_detail.id
where products_detail.hard_drive like '%SSD%'
Select * from products_detail
create table category(
	id int identity(1,1) primary key,
	name nvarchar(100) not null,
	actived bit not null,
	parent_id int -- khóa ngoại của bảng
)
Select * from products join category on products.id_category = category.id
where category.parent_id =58
/*-------------------------------------------------------BẢNG THỂ LOẠI----------------------------------------------------------------------------*/
Select * from sale_product
where  GETDATE() between date_on and date_off
where status like 1
Select * from category
where parent_id =1 or id = 76
insert into category(name,actived,parent_id) values 
('Laptop',1, null),
/* Dell */
('Dell',1, 1), 
('Dell Latitude',1, 2),
('Dell XPS',1, 2),
('Dell Precision',1, 2),
('Dell Inspiron',1, 2),
('Dell Gaming G Series',1, 2),
('Dell Alienware',1, 2),
('Dell Vostro',1, 2),

/* Apple */
('Apple',1, 1),
('MacBook Air ',1, 10),
('MacBook Pro',1, 10),

/* Asus */
('Asus',1, 1),
('ExpertBook',1, 13),
('VivoBook',1, 13),
('ZenBook',1, 13),
('TUF/ROG Gaming',1, 13),
('ProArt Studio',1, 13),

/* Acer */
('Acer',1, 1),
('Nitro',1, 19),
('Predator',1, 19),
('Aspire',1, 19),
('Swift',1, 19),

/* Lenovo */
('Lenovo',1, 1),
('Thinkpad',1, 24),
('IdeaPad',1, 24),
('Legion',1, 24),
('ThinkBook',1, 24),

/* Razer */
('Razer',1, 1),
('Blade 14',1, 29),
('Blade 15',1, 29),
('Blade 17',1, 29),
('Blade Stealth',1, 29),

/* HP */
('HP',1, 1),
('Elitebook',1, 34),
('ZBook',1, 34),
('Pavilion',1, 34),
('14s/15s',1, 34), 
('Envy',1, 34),
('Omen',1, 34),

/* Microsoft */
('Microsoft',1, 1),
('Surface Book',1, 41),
('Surface Laptop',1, 41),
('Surface Pro',1, 41),


/* MSI */
('MSI',1, 1),
('GF Series',1, 45),
('Prestige',1, 45),
('Modern',1, 45),
('Alpha',1, 45),
('Bravo',1, 45),
('GL Series',1, 45),
('GE Series',1, 45),
('GS Series',1, 45),
('GT Series',1, 45),

/* Lg */
('Lg',1, 1),
('Gram',1, 55)

Select * from products join products_detail on products.id = products_detail.id
where products_detail.vga like '%Intel Iris%'
where products_detail.display_size like '%13.3%'
/*------------------------ PC-------------------        */
insert into category(name,actived,parent_id) values 
('PC Văn Phòng',1, null), --57
/* Dell */
('Máy Tính Văn Phòng Dell',1, 57), 
('Dell OptiPlex',1, 58),
('Dell Inspiron',1, 58),
('Dell Vostro',1, 58),
('Dell XPS',1, 58),

/* HP */
('Máy Tính Văn Phòng HP',1, 57), --63
('HP 280',1, 63),
('HP 290',1, 63),
('HP 390',1, 63),
('HP Pavilion',1, 63),
('HP ProDesk',1, 63),
('HP EliteDesk',1, 63),
('HP ProOne',1, 63),
('HP Pro',1, 63),
('HP EliteOne',1, 63), -- Trừ 3

/* Asus */
('Máy Tính Văn Phòng Asus',1, 57),

/* Lenovo */
('Máy Tính Văn Phòng Lenovo',1, 57), 

/* Acer */
('Máy Tính Văn Phòng Acer',1, 57)

/*------------------------ Màn hình máy tính-------------------        */
insert into category(name,actived,parent_id) values 
('Màn Hình',1, null), --76
('Màn Hình Dell',1, 76), --77
('Màn Hình Dell UltraSharp',1, 77),
('Màn Hình Dell E-Series',1, 77),
('Màn Hình Dell S-Series',1, 77),
('Màn Hình Dell Gaming',1, 77),
--Sửa lại phần bên dưới

('Màn Hình Acer',1, 76), 
('Màn Hình AOC',1, 76), 
('Màn Hình Apple',1, 76), 
('Màn Hình Asus',1, 76), 
('Màn Hình BenQ',1, 76), 
('Màn Hình Concept D',1, 76), 
('Màn Hình Gigabyte',1, 76), 
('Màn Hình HKC',1, 76), 
('Màn Hình HP',1, 76), 
('Màn Hình HuntKey',1, 76), 
('Màn Hình Lenovo',1, 76), 
('Màn Hình LG',1, 76), 
('Màn Hình MSI',1, 76), 
('Màn Hình Samsung',1, 76),
('Màn Hình ViewSonic',1, 76)


/*-----------------------------------------------------------------------------------------------------------------------------------*/

--Tạo 1 bảng sale 
create table sale_product(
	sale_code varchar(9) primary key,
	promotion_type nvarchar(50) not null,--loại | loại sản phẩm | năm | dịp tết | mới nhập
	date_on date not null,
	date_off date not null,
	promotion int not null,
	quantity int, --số lượng nếu không nhập là dùng bnh cx được
	status bit,
)

/*---------------------------------------------------BẢNG SALE--------------------------------------------------------------------------------*/
Select CONVERT(nvarchar(50), date_on, 25) from sale_product
insert into sale_product(sale_code, promotion_type,date_on,date_off,promotion,quantity,status) values 
	('SL007', N'Giảm giác sốc','2021-10-14', '2021-10-15 23:59:59',10,null,0), 
	('SL002', N'Dell cùng bạn vui đến trường','2021-10-01', '2021-10-15',10,300,1), 
	('SL003', N'Chợ tết laptop - ngập tràn khuyến mãi','2021-11-01', '2021-11-18',20,500,1), 
	('SL004', N'Tâm điểm khai trường – chọn quà chất tôi','2021-01-15', '2021-10-30',5,400,0), 
	('SL005', N'Máy chuẩn gu, quà vi vu','2021-05-20', '2021-06-05',15,200,0)
/*-----------------------------------------------------------------------------------------------------------------------------------*/


--Sản phẩm
create table products(
	id varchar(25) primary key, --Ảnh chính + list ảnh của image_detail 
	name nvarchar(100) not null,
	date_on date not null,
	id_category int not null,
	type_of_item int not null,
	input_price float,
	output_price float,
	quantity int,
	mass float, --Khối lượng
	unit nvarchar(15) not null,--Đơn vị tính
	release_date varchar(4), --Ngày phát hành
	date_of_manufacture varchar(10), -- ngày sản xuất
	on_sale varchar(9), --Khóa ngoại bảng sale
	active bit
)


--Khóa ngoại sản phẩm 1-1 danh mục | 1-1 sale - thay đổi được 
ALTER TABLE products ADD FOREIGN KEY (id_category) REFERENCES category(id);	

ALTER TABLE products ADD FOREIGN KEY (on_sale) REFERENCES sale_product(sale_code);	

/*-----------------------------------------------------------------BẢNG SẢN PHẨM------------------------------------------------------------------*/
insert into products values
	-- lap top
	('MQD32SA', N'Macbook Air MQD32SA/A i5 5350U','2020-11-05',11, 1,19000000,23990000,40,1.35,'1 cái','2021','05-2021','SL001',1),
	('DL3520I5', N'Dell Latitude 3520 Intel (Chính hãng)','2021-01-05',3, 1,12000000,15990000 ,80,1.79,'1 cái','2021','06-2021','SL001',1),
	('APH30021', N'Acer Predator Helios 300 2021','2020-12-15', 21, 1,29000000,33990000 ,40,2.3,'1 cái','2021','08-2021','SL003',1),
	('DV3400I5', N'Dell Vostro 14 3400 Intel gen 11 (Chính Hãng)','2020-11-05', 9, 1 ,15000000,18990000,35,1.64,'1 cái','2021','04-2021','SL003',1),
	('LI314R5', N'Lenovo IdeaPad 3 14 (AMD Ryzen 5000)','2021-05-05', 26, 1,10000000,14290000,30,1.41,'1 cái','2021','06-2021','SL001',1),
	('AS514', N'Acer Swift 5 14 (Chính hãng)','2021-05-05', 23, 1,22000000,26990000,20,1,'1 cái','2021','10-2021','SL005',1),
	('LYS714ITL05',N'Laptop Lenovo Yoga Slim 7 14ITL05','2021-07-10', 24, 1,2300000,26490000,13,1.36,N'1 cái','2021','05-2021','SL003',1),
	('HPZB15G5',N'Laptop Workstation HP Zbook 15 G5 3AX12AV','2021-10-15', 36, 1,43990000,43990000,10,2.6,N'1 cái','2021','10-2021',null,1),
	('MBPR13MWP52',N'Apple Macbook Pro 13 Touchbar (MWP52)','2020-10-15',12, 1,21600000,23990000,30,1.4,N'1 cái','2020','08-2020',null,1),
	-- PC
	('PCDOAIO7080',N'PC Dell OptiPlex All in One 7480','2021-09-30', 59, 57,29600000,30299000,5,5.94,N'1 cái','2021','05-2021',null,1),
	('PCAPN608250U',N'PC Mini Asus PN60 i5-8250U','2021-10-15', 73, 57,7000000,8349000,5,0.7,N'1 cái','2021','10-2021','SL002',1),
	('PCHP280G3SFF',N'PC HP 280 G3 SFF','2020-12-27',64, 57,8000000,8989000,13,5.94,N'1 cái','2021','08-2020','SL001',1),

	--Màn hình
	('MAVG240YS' ,N'Màn hình Acer VG240YS','2021-10-16', 82, 76,4000000,6259000,15,3.85,'1 cái','2020','2020-05-01','SL001',1),
	('MATGVG249Q', N'Màn hình Asus TUF GAMING VG249Q','2021-09-26', 85,76,4500000,6390000,10,5.6,'1 cái','2021','2021-01-01','SL003',1),
	('MDE2020H', N'Màn hình Dell E2020H','2021-04-12',79, 76,2399000,4399000,10,2.94,'1 cái','2021', '2021-01-01','SL003',1),
	('MHM24F', N'Màn hình HP M24F','2021-05-15', 90, 76,3500000,4849000,5,2.5,'1 cái','2021', '2021-05-01','SL003',1),
	('MHM27FW', N'Màn hình HP M27FW','2020-02-15',90, 76,4500000,6059000,10,2.5,'1 cái','2021', '2021-02-02','SL004',1);
/*-----------------------------------------------------------------------------------------------------------------------------------*/

create table products_detail(
	id varchar(25) primary key not null,
	--tt chung
	producer nvarchar(150), --Nhà sản xuất
	-- laptop
	cpu nvarchar(150),
	color nvarchar(150),
	ram nvarchar(150),
	hard_drive nvarchar(100),
	webcam nvarchar(100),
	vga nvarchar(100),-- card đồ họa
	operating_system nvarchar(100), --Hệ điều hành
	--PC
	power nvarchar(100), --Nguồn
	--Màn hình
	display_size nvarchar(100), --Kích thước màn hình
	screen_ratio nvarchar(100), --Tỉ lệ màn hình
	scan_frequency nvarchar(100), --tần số quét
	background_panels varchar(9), --tấm nền
	resolution varchar(35), --Độ phân giải
	contrast varchar(35), --Độ tương phản
	response_time varchar(9), --Thời gian phản hồi
	brightness varchar(9), -- Độ sáng
	view_screenshots varchar(9), --Góc nhìn
	warranty_period int not null, --Thời gian bảo hành
	accessories_included ntext, --Phụ kiện đi kèm
	see_more ntext, --Xem thêm
)
độ phân gảii loại pc,
tần số quét loại pc
cart đồ hoạ loại màn hình
INSERT INTO dbo.[products_detail] VALUES 
('APH30021','Acer','Intel® Core™ i7-11800H (2.4Ghz/24MB cache)– CPU thế hệ 11 mới nhất','Đen','16GB DDR4 3200Mhz (2* 8GB)'
,'512GB SSD PCIe NVMe (nâng cấp tối đa 2TB SSD PCIe NVMe và 2TB HDD 2.5-inch 5400 RPM)'
,'HD','NVIDIA® GeForce® RTX 3060 6G-GDDR6','Win 10 Home',null,'15.6 inch QHD (2560x1440) 165Hz SlimBezel'
,'(2560x1440)',null,null,'2K',null,null,'300nits',null,24,'Cable + Sạc','none'),

('AS514','Acer','Intel® Core™ i5-1135G7 Processor','Safari Gold','8GB LPDDR4X Onboard'
,'512GB SSD PCIe NVMe '
,'HD','1 x HDMI','Win 10 Home',null,'14Inch FHD IPS high-brightness (340 nits)'
,'90x80',null,null,'FHD (1920 x 1080)',null,null,null ,null,24,'none','none'),

('DL3520I5','Dell','Intel Core™ i5 1135G7 (2.4GHz, 8MB Cache)','Đen','8GB DDR4 3200Mhz (1*8GB) '
,'256GB PCIe NVMe Class 35 M2 SSD (có slot 2.5 inch) '
,'HD',' Intel® Iris® Xe Graphics','Fedora',null,'15.6 inch'
,'90x80',null,null,'1920 x 1080',null,null,null,null,24,'AC Adapter','none'),

('DV3400I5','Dell','Intel Core i5 1135G7 (2.4Ghz/8MB cache)','Đen','8GB DDR4 3200Mhz '
,'256GB PCIe NVMe SSD  '
,'HD',' Intel® Iris® Xe Graphics','Win 10 Home SL 64',null,'14.0-inch'
,'90x80',null,null,'FHD (1920 x 1080)',null,null,'220nits',null,24,'AC Adapter','none'),

('LI314R5','Lenovo','AMD Ryzen 7 5700U (8C / 16T, 1.8 / 4.3GHz, 4MB L2 / 8MB L3)','Arctic Grey','4GB Soldered DDR4-3200 + 4GB SO-DIMM DDR4-3200'
,'512GB SSD '
,'720p with Privacy Shutter','Integrated AMD Radeon Graphics','Windows 10 Home 64, English',null,'14.0-inch'
,'90x80',null,null,'45% NTSC',null,null,'250nits',null,24,' Adapter, tài liệu,','none'),

('MQD32SA','Lenovo','Intel Core i5 Broadwell','Trắng','8 GB'
,'128 GB SSD'
,'HD',null,'Mac OS',null,'13.3 inch'
,'90x80',null,null,null,null,'1600 MHz','250nits',null,24,' Adapter, tài liệu,','none')

---Laptop Product detail
/*
insert into products_detail(id,producer,cpu,color,ram,hard_drive,webcam,vga,operating_system,display_size,warranty_period,accessories_included) values 
('LYS714ITL05','Lenovo','Intel Core i7 1165G7',N'Xanh rêu','8GB','512GB SSD',N'720p + IR + ToF','Onboard','Win10','320.6mm x 208.18mm x 14.9mm',12,'Power Adapter'),

('HPZB15G5','HP',N'Coffeelake i7-8750H (2.2 GHz, up to 4.1 GHz with Turbo Boost, 9MB cache, 6 core)',N'',N'16GB DDR4 2666Mhz (1*16GB)',
	N'256GB SSD PCIe NVMe TLC','HD',N'NVIDIA Quadro P2000 4GB',N'DOS','',12,N'AC Slim adapter'),

(N'MBPR13MWP52','Apple',N'Intel Core i5 2.0Ghz',N'Xám','16GB','1TB SSD',N'720p HD',N'Onboard',N'Mac OS','',12,N'AC Adapter')*/

/*-----------------------------------------------------------------------------------------------------------------------------------*/
-- PC Product detail
insert into products_detail(id,producer,cpu,ram,hard_drive,vga,operating_system,display_size,warranty_period) values 
	('PCDOAIO7080','Dell','Core i7-10700',N'8GB','512GB SSD',N'Onboard',N'Ubuntu',N'33.4 x 54 x 5.28',12),
	('PCAPN608250U','Asus','i5-8250U',N'Không đi kèm (max 32GB)',N'Không đi kèm',N'Intel® UHD Graphics 620',N'',N'11.5 x 11.5 x 4.9',12),
	('PCHP280G3SFF','HP','Core i3-9100','4GB',N'1TBB HDD',N'Intel® UHD Graphics 630','DOS','9.5 x 29.6 x 27',12)
insert into products_detail(id,producer,display_size,screen_ratio,scan_frequency,background_panels,resolution,contrast,response_time,brightness,view_screenshots,warranty_period) values
	('MAVG240YS', N'Acer','23.8 inch','16:09','165Hz','IPS','1920x1080','1000:1','0.5ms','250 nits','178°/178°',36), 
	('MATGVG249Q', N'Asus','23.8 inch','16:09','144Hz','IPS','1920x1080','1000:1','1ms','250 nits','178°/178°',12),
	('MDE2020H', N'Dell','19.5 inch','16:09','60Hz','TN','1600x900','1000:1','5ms','250 nits','160/170',24),
	('MHM24F', N'HP','23.8 inch','16:9','165Hz','IPS','1920x1080','1000:1','5ms','300cd/m2','178°/178°',36),
	('MHM27FW', N'HP','27 inch','16:9','60Hz','IPS','1920x1080','1000:1','5ms','300cd/m2','178°/178°',36);

--Khóa ngoại ct sản phẩm 1-1 sản phẩm
ALTER TABLE products_detail ADD FOREIGN KEY (id) REFERENCES products(id);	

-- Chi tiết hình ảnh sản phẩm
create table image_detail(
	id int identity(1,1) primary key,
	id_product varchar(25) not null,
	name_path nvarchar(150) not null,
)
--Khóa ngoại ct hình ảnh N-1 sản phẩm
ALTER TABLE image_detail ADD FOREIGN KEY (id_product) REFERENCES products(id);	

--Tin tức
create table blogs(
	title nvarchar(255) primary key not null,
	id_account int not null,
	date_created date not null,
	img nvarchar(150),
)
--Khóa ngoại blogs N-1
ALTER TABLE blogs ADD FOREIGN KEY (id_account) REFERENCES account(id);

--Mô tả 
Create table description(
	id int identity(1,1) primary key,
	id_product varchar(25) not null,
	id_blog nvarchar(255),
	title nvarchar(255),
	content ntext,
)
--Khóa ngoại mô tả N-1 sản phẩm
ALTER TABLE description ADD FOREIGN KEY (id_product) REFERENCES products(id);	

ALTER TABLE description ADD FOREIGN KEY (id_blog) REFERENCES blogs(title);

--Phân lô hàng nhập
create table imported_shipment( --Nếu giá tiền thay đổi thống kê tiền lãi lấy giá bán từng đợt nhập trừ đi giá nhập bên lô nhập)
	id int identity(1,1) primary key,
	date_added date not null,
	id_account int not null,
	supplier nvarchar(255),
	status bit,
)
--Khóa ngoạilô nhập 1-1 account
ALTER TABLE imported_shipment ADD FOREIGN KEY (id_account) REFERENCES account(id);	

--Chi tiết lô nhập
create table shipment_detail(
	id_shipment int not null,
	id_product varchar(25) not null,
	price float not null, --
	quantity int not null, --
	primary key(id_shipment, id_product),
)
--Khóa ngoại ct lô nhập N-1 lô nhập | 1-1 sản phẩm
ALTER TABLE shipment_detail ADD FOREIGN KEY (id_shipment) REFERENCES imported_shipment(id);	

ALTER TABLE shipment_detail ADD FOREIGN KEY (id_product) REFERENCES products(id);	

--Đánh giá sản phẩm
CREATE TABLE product_rating(
	id_product varchar(25) not null,
	id_account int not null,
	image nvarchar(150),
	star_rating int not null,
	comment ntext,
)
--Khóa ngoại đánh giá N-1 sản phẩm
ALTER TABLE product_rating ADD FOREIGN KEY (id_product) REFERENCES products(id);

ALTER TABLE product_rating ADD FOREIGN KEY (id_account) REFERENCES account(id);

--Khuyến mãi
Create table vouchers(
	id nvarchar(10) primary key not null,
	id_category int, --Không nhập id sản phẩm hoặc danh mục sẽ áp dụng toàn bộ sản phẩm
	id_product varchar(25),
	name nvarchar(50) not null,
	promotion int not null,
	start_day date not null,
	end_date date not null,
	description nvarchar(150),
	quantity int,
	actived bit,
)
--Khóa ngoại vouchers 1-1 cả 2 bảng
ALTER TABLE vouchers ADD FOREIGN KEY (id_category) REFERENCES category(id);

ALTER TABLE vouchers ADD FOREIGN KEY (id_product) REFERENCES products(id);

--Địa chỉ giao hàng
Create table delivery_address(
	id int identity(1,1) primary key,
	name nvarchar(100),
	phone varchar(11),
	address nvarchar(100),
	set_as_default bit,
	id_account int,
)
--Khóa ngoại địa chỉ N-1 người dùng
ALTER TABLE delivery_address ADD FOREIGN KEY (id_account) REFERENCES account(id);

--Bảng mua hàng
Create table orders( -- Trả hàng | Đổi hàng | Bảo hành 
	id int identity(1,1) primary key,
	id_account int,
	order_date date,
	phone_number varchar(10) not null,
	address nvarchar(60) not null,
	quantity int not null,
	description ntext
)
--Khóa ngoại orders N-1 người dùng
ALTER TABLE orders ADD FOREIGN KEY (id_account) REFERENCES account(id);

--Chi tiết
Create table order_details(
	id int identity(1,1) primary key,
	id_order int,
	id_product varchar(25),
	id_voucher nvarchar(10),
	price float not null,
	quantity int not null,
	completion_date date,--Đã nhận được hàng
	received int, --0 chờ xác nhận | 1 Đang giao | 2 Đã giao | 3 Bảo hành | 4 Đổi hàng
)

--Khóa ngoại order_details 1-1 voucher | 1-1 order | 1-1 product
ALTER TABLE order_details ADD FOREIGN KEY (id_order) REFERENCES orders(id);

ALTER TABLE order_details ADD FOREIGN KEY (id_product) REFERENCES products(id);

ALTER TABLE order_details ADD FOREIGN KEY (id_voucher) REFERENCES vouchers(id);

--Liên hệ
create table contact(
	id int identity(1,1) primary key,
	name nvarchar(150) not null,
	email varchar(150) not null,
	subject nvarchar(255) not null,
	message ntext not null,
)
select * from category
