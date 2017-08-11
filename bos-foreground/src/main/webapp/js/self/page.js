function ctrlRead($scope, $filter) {
	// init
	$scope.sortingOrder = sortingOrder;
	$scope.reverse = false;
	$scope.filteredItems = [];
	$scope.groupedItems = [];
	$scope.itemsPerPage = 4;
	$scope.pagedItems = [];
	$scope.currentPage = 0;
	$scope.items = [{
			"id": 1,
			"titleImg": "images/show/suyun/promotion1.png",
			"title": "进入六月，中俄跨境电商物流服务又添 喜讯：速运快递特惠开通了中国大陆...",
			"startDate": "20xx.xx.xx",
			"endDate": "20xx.xx.xx",
			"activescope": "中国大陆",
			"status": "进行中"
		}, {
			"id": 2,
			"titleImg": "images/show/suyun/promotion2.png",
			"title": "疯狂夏寄，速运三重惠，给利益享不停 手机支付运费返5%",
			"startDate": "20xx.xx.xx",
			"endDate": "20xx.xx.xx",
			"activescope": "中国大陆",
			"status": "进行中"
		}, {
			"id": 3,
			"titleImg": "images/show/suyun/promotion3.png",
			"title": "速运快递再开通中国至印尼、印度、柬 埔寨三国快递服务！",
			"startDate": "20xx.xx.xx",
			"endDate": "20xx.xx.xx",
			"activescope": "中国大陆",
			"status": "进行中"
		}, {
			"id": 4,
			"titleImg": "images/show/suyun/promotion4.png",
			"title": "关于杭州举办“20国集团领导人第1x次 峰会”期间我司收派服务调整的通知...",
			"startDate": "20xx.xx.xx",
			"endDate": "20xx.xx.xx",
			"activescope": "中国大陆",
			"status": "进行中"
		}, {
			"id": 5,
			"titleImg": "images/show/suyun/promotion2.png",
			"title": "疯狂夏寄，速运三重惠，给利益享不停 手机支付运费返5%",
			"startDate": "20xx.xx.xx",
			"endDate": "20xx.xx.xx",
			"activescope": "中国大陆",
			"status": "进行中"
		},{
			"id": 6,
			"titleImg": "images/show/suyun/promotion3.png",
			"title": "速运快递再开通中国至印尼、印度、柬 埔寨三国快递服务！",
			"startDate": "20xx.xx.xx",
			"endDate": "20xx.xx.xx",
			"activescope": "中国大陆",
			"status": "进行中"
		}, {
			"id": 7,
			"titleImg": "images/show/suyun/promotion1.png",
			"title": "进入六月，中俄跨境电商物流服务又添 喜讯：速运快递特惠开通了中国大陆...",
			"startDate": "20xx.xx.xx",
			"endDate": "20xx.xx.xx",
			"activescope": "中国大陆",
			"status": "进行中"
		}, {
			"id": 8,
			"titleImg": "images/show/suyun/promotion4.png",
			"title": "关于杭州举办“20国集团领导人第1x次 峰会”期间我司收派服务调整的通知...",
			"startDate": "20xx.xx.xx",
			"endDate": "20xx.xx.xx",
			"activescope": "中国大陆",
			"status": "进行中"
		}

	];

	var searchMatch = function(haystack, needle) {
		if(!needle) {
			return true;
		}
		return haystack.toLowerCase().indexOf(needle.toLowerCase()) !== -1;
	};

	// init the filtered items
	$scope.search = function() {
		$scope.filteredItems = $filter('filter')($scope.items, function(item) {
			for(var attr in item) {
				if(searchMatch(item[attr], $scope.query))
					return true;
			}
			return false;
		});
		// take care of the sorting order
		if($scope.sortingOrder !== '') {
			$scope.filteredItems = $filter('orderBy')($scope.filteredItems, $scope.sortingOrder, $scope.reverse);
		}
		$scope.currentPage = 0;
		// now group by pages
		$scope.groupToPages();
	};

	// calculate page in place
	$scope.groupToPages = function() {
		$scope.pagedItems = [];

		for(var i = 0; i < $scope.filteredItems.length; i++) {
			if(i % $scope.itemsPerPage === 0) {
				$scope.pagedItems[Math.floor(i / $scope.itemsPerPage)] = [$scope.filteredItems[i]];
			} else {
				$scope.pagedItems[Math.floor(i / $scope.itemsPerPage)].push($scope.filteredItems[i]);
			}
		}
	};

	$scope.range = function(start, end) {
		var ret = [];
		if(!end) {
			end = start;
			start = 0;
		}
		for(var i = start; i < end; i++) {
			ret.push(i);
		}
		return ret;
	};

	$scope.prevPage = function() {
		if($scope.currentPage > 0) {
			$scope.currentPage--;
		}
	};

	$scope.nextPage = function() {
		if($scope.currentPage < $scope.pagedItems.length - 1) {
			$scope.currentPage++;
		}
	};

	$scope.setPage = function() {
		$scope.currentPage = this.n;
	};

	// functions have been describe process the data for display
	$scope.search();

	// change sorting order
	$scope.sort_by = function(newSortingOrder) {
		if($scope.sortingOrder == newSortingOrder)
			$scope.reverse = !$scope.reverse;

		$scope.sortingOrder = newSortingOrder;

		// icon setup
		$('th i').each(function() {
			// icon reset
			$(this).removeClass().addClass('icon-sort');
		});
		if($scope.reverse)
			$('th.' + new_sorting_order + ' i').removeClass().addClass('icon-chevron-up');
		else
			$('th.' + new_sorting_order + ' i').removeClass().addClass('icon-chevron-down');
	};
};
ctrlRead.$inject = ['$scope', '$filter'];