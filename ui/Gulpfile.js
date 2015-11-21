var gulp = require('gulp'),
    sass = require('gulp-sass'),
    livereload = require('gulp-livereload');


gulp.task('styles', function() {
  gulp.src('sass/**/*.scss')
      .pipe(sass({
        includePaths: ['node_modules/bootstrap-sass/assets/stylesheets'],
      }).on('error', sass.logError))
      .pipe(gulp.dest('../resources/public/css/'))
      .pipe(livereload());
});

gulp.task('default',function() {
  livereload.listen();
  gulp.watch('sass/**/*.scss',['styles'])
});


