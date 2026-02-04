export interface CampaignModel {
    id: string,
    title: string,
    description: string,
    createdAt?: string,
    ownerName: string,
    donations: number
    goal: string
    raised: string
}