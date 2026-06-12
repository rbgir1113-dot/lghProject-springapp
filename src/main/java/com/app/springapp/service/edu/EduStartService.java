package com.app.springapp.service.edu;

public interface EduStartService {

    // 학습 시작 기록 저장
    public void startEdu(Long userId, Long eduId);

    // 학습 세션 완료 처리
    public void completeEduStart(Long userId, Long eduId);

    // 학습 세션 완료 여부 조회
    public boolean isEduStartCompleted(Long userId, Long eduId);

    // 학습 로드맵 이벤트 보상 수령
    public int claimRoadmapReward(Long userId, Long eduId);

}
