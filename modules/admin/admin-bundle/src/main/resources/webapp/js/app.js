'use strict';

/* App Module */

angular.module('motech-admin', ['motech-dashboard', 'bundleServices', 'messageServices', 'platformSettingsServices',
    'moduleSettingsServices', 'logService', 'ngCookies', 'bootstrap']).config(['$routeProvider', function($routeProvider) {
      $routeProvider.
          when('/bundles', {templateUrl: '../admin/partials/bundles.html', controller: BundleListCtrl}).
          when('/messages', {templateUrl: '../admin/partials/messages.html', controller: StatusMsgCtrl}).
          when('/settings', {templateUrl: '../admin/partials/settings.html', controller: SettingsCtrl}).
          when('/bundle/:bundleId', {templateUrl: '../admin/partials/bundle.html', controller: ModuleCtrl}).
          when('/bundleSettings/:bundleId', {templateUrl: '../admin/partials/bundleSettings.html', controller: BundleSettingsCtrl}).
          when('/modulePanels', {templateUrl: '../admin/partials/modulePanels.html'}).
          when('/operations', {templateUrl: '../admin/partials/operations.html', controller: OperationsCtrl}).
          when('/log', {templateUrl: '../admin/partials/log.html', controller: ServerLogCtrl}).
          when('/logOptions', {templateUrl: '../admin/partials/logOptions.html', controller: ServerLogOptionsCtrl}).
          otherwise({redirectTo: '/bundles'});
}]).filter('moduleName', function () {
    return function (input) {
        return input.replace(/(motech\s|\sapi|\sbundle)/ig, '');
    }
}).directive('sidebar', function () {
   return function (scope, element, attrs) {
       $(element).sidebar({
           position:"right"
       });
   }
});
