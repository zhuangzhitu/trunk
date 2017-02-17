package com.fourmob.datetimepicker.date;

/**
 * 摘取自https://github.com/flavienlaurent/datetimepicker
 *
 * @author kymjs
 */
interface DatePickerController {
    int getFirstDayOfWeek();

    int getMaxYear();

    int getMinYear();

    SimpleMonthAdapter.CalendarDay getSelectedDay();

    void onDayOfMonthSelected(int year, int month, int day);

    void onYearSelected(int year);

    void registerOnDateChangedListener(
            DatePickerDialog.OnDateChangedListener onDateChangedListener);

    void tryVibrate();
}